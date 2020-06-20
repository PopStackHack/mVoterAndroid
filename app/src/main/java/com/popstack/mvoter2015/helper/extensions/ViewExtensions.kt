package com.popstack.mvoter2015.helper.extensions

import android.view.LayoutInflater
import android.view.ViewGroup

/**
 * Created by Vincent on 2/13/20
 */
fun ViewGroup.inflater(): LayoutInflater {
  return this.context.inflater()
}