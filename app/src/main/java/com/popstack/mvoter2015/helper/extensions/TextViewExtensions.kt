package com.popstack.mvoter2015.helper.extensions

import android.annotation.SuppressLint
import android.graphics.drawable.Drawable
import android.text.Layout
import android.widget.TextView

fun TextView.setCompoundDrawablesKt(
  left: Drawable? = null,
  right: Drawable? = null,
  top: Drawable? = null,
  bottom: Drawable? = null
) {
  this.setCompoundDrawables(left, top, right, bottom)
}

fun TextView.setCompoundDrawableWithIntrinsicBoundsKt(
  start: Drawable? = null,
  end: Drawable? = null,
  top: Drawable? = null,
  bottom: Drawable? = null
) {
  this.setCompoundDrawablesRelativeWithIntrinsicBounds(start, top, end, bottom)
}

@SuppressLint("WrongConstant")
fun TextView.justify() {
  if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
    justificationMode = Layout.JUSTIFICATION_MODE_INTER_WORD
  }
}