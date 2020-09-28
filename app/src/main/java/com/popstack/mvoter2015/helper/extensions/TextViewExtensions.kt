package com.popstack.mvoter2015.helper.extensions

import android.graphics.drawable.Drawable
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