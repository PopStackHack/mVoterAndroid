package com.aungkyawpaing.simplespinneradapter

import android.content.Context

class StringSimpleSpinnerAdapter(itemList: List<String> = listOf()) :
  SimpleSpinnerAdapter<String>(itemList) {

  override fun getDisplayString(position: Int, context: Context): String {
    return getItem(position)
  }

  override fun getItemId(position: Int): Long {
    return position.toLong()
  }

}