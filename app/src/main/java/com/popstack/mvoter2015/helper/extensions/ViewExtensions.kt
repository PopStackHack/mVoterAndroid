package com.popstack.mvoter2015.helper.extensions

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.popstack.mvoter2015.helper.extensions.inflater

/**
 * Created by Vincent on 2/13/20
 */
fun View.setVisible(isVisible: Boolean) {
  if (isVisible) {
    this.visibility = View.VISIBLE
  } else {
    this.visibility = View.GONE
  }
}

fun ViewGroup.inflater(): LayoutInflater {
  return this.context.inflater()
}