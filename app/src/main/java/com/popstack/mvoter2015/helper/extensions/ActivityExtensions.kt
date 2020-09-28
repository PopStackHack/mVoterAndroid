package com.popstack.mvoter2015.helper.extensions

import android.app.Activity
import android.view.View
import androidx.annotation.StringRes
import com.google.android.material.snackbar.BaseTransientBottomBar.Duration
import com.google.android.material.snackbar.Snackbar

/**
 * Created by Vincent on 2/13/20
 */
fun Activity.showSnackbar(text: CharSequence, @Duration duration: Int) {
  val contentView = findViewById<View>(android.R.id.content)
  val snackbar = Snackbar.make(contentView, text, duration)
  snackbar.show()
}

fun Activity.showSnackbar(@StringRes stringResId: Int, @Duration duration: Int) {
  val contentView = findViewById<View>(android.R.id.content)
  val snackbar = Snackbar.make(contentView, stringResId, duration)
  snackbar.show()
}

fun Activity.hideKeyboard() {
  //Find the currently focused view, so we can grab the correct window token from it.
  var view = currentFocus
  //If no view currently has focus, create a new one, just so we can grab a window token from it
  if (view == null) {
    view = View(this)
  }
  view.hideKeyboard()
}

fun Activity.showKeyboard() {
//Find the currently focused view, so we can grab the correct window token from it.
  var view = currentFocus
  //If no view currently has focus, create a new one, just so we can grab a window token from it
  if (view == null) {
    view = View(this)
  }
  view.showKeyboard()
}