package com.popstack.mvoter2015.helper.extensions

import android.app.Activity
import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowInsets
import android.view.inputmethod.InputMethodManager
import androidx.core.view.doOnLayout

/**
 * Created by Vincent on 2/13/20
 */
fun ViewGroup.inflater(): LayoutInflater {
  return this.context.inflater()
}

fun View.hideKeyboard() {
  if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
    doOnLayout {
      windowInsetsController?.hide(WindowInsets.Type.ime())
    }
  } else {
    val imm = context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(windowToken, 0)
  }
  clearFocus()
}

fun View.showKeyboard() {
  requestFocus()
  if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
    doOnLayout {
      windowInsetsController?.show(WindowInsets.Type.ime())
    }
  } else {
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.showSoftInput(this, InputMethodManager.SHOW_IMPLICIT)
  }
}