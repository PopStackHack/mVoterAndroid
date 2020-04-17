package com.popstack.mvoter2015.helper.extensions

import android.content.Context
import android.view.LayoutInflater
import android.widget.Toast
import androidx.annotation.StringRes

/**
 * Created by Vincent on 2/13/20
 */
fun Context.inflater(): LayoutInflater {
  return LayoutInflater.from(this)
}

fun Context.showShortToast(message: CharSequence) {
  Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

fun Context.showShortToast(@StringRes resId: Int) {
  Toast.makeText(this, resId, Toast.LENGTH_SHORT).show()
}

fun Context.showShortToast(@StringRes resId: Int, vararg formatArgs: String) {
  val text = getString(resId, formatArgs)
  Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
}

fun Context.showLongToast(message: CharSequence) {
  Toast.makeText(this, message, Toast.LENGTH_LONG).show()
}

fun Context.showLongToast(@StringRes resId: Int, vararg formatArgs: String) {
  val text = getString(resId, formatArgs)
  Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
}

fun Context.showLongToast(@StringRes resId: Int) {
  Toast.makeText(this, resId, Toast.LENGTH_LONG).show()
}