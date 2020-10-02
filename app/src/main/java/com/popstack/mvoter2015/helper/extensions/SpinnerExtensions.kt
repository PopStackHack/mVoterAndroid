package com.popstack.mvoter2015.helper.extensions

import android.view.View
import android.widget.AdapterView
import android.widget.Spinner
import timber.log.Timber

/**
 * Created by Vincent on 2/13/20
 */
fun Spinner.safeSelection(
  position: Int,
  onError: ((Exception) -> (Unit)) = { error ->
    Timber.e(error)
  },
  doAfterSelect: ((Int) -> (Unit)) = { _ ->
    //Do Nothing
  }
) {
  try {
    setSelection(position)
    doAfterSelect(position)
  } catch (e: Exception) {
    onError.invoke(e)
  }

}

fun Spinner.setOnItemSelectedListener(
  onNothingSelect: (@ParameterName("parent") AdapterView<*>?) -> Unit = { _ -> },
  onItemSelect: (
    @ParameterName("parent") AdapterView<*>?,
    @ParameterName("view") View?,
    @ParameterName("position") Int,
    @ParameterName("id") Long
  ) -> Unit = { _, _, _, _ -> }
): AdapterView.OnItemSelectedListener {

  return object : AdapterView.OnItemSelectedListener {

    override fun onNothingSelected(parent: AdapterView<*>?) {
      onNothingSelect.invoke(parent)
    }

    override fun onItemSelected(
      parent: AdapterView<*>?,
      view: View?,
      position: Int,
      id: Long
    ) {
      onItemSelect.invoke(parent, view, position, id)
    }

  }.also {
    this.onItemSelectedListener = it
  }
}