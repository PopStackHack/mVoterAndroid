package com.popstack.mvoter2015.helper.extensions

import android.util.Log
import android.widget.Spinner

/**
 * Created by Vincent on 2/13/20
 */
fun Spinner.safeSelection(
  position: Int,
  onError: ((Exception) -> (Unit)) = { error ->
    Log.e("SpinnerExtensions", Log.getStackTraceString(error))
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