package com.popstack.mvoter2015.font

import android.content.Context
import android.view.ViewGroup
import android.widget.TextView

/**
 * Modified code from MDetect
 * Source: https://github.com/dev-myatminsoe/MDetect/blob/master/mdetect/src/main/java/me/myatminsoe/mdetect/MDetect.kt
 */
object MDetect {

  private var detectedFont: BurmeseFont? = null

  fun identifyFont(context: Context): BurmeseFont {
    if (detectedFont != null) {
      return detectedFont!!
    }

    val textView = TextView(context, null)
    textView.layoutParams = ViewGroup.LayoutParams(
      ViewGroup.LayoutParams.WRAP_CONTENT,
      ViewGroup.LayoutParams.WRAP_CONTENT
    )

    textView.text = "\u1000"
    textView.measure(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
    val length1 = textView.measuredWidth

    textView.text = "\u1000\u1039\u1000"
    textView.measure(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
    val length2 = textView.measuredWidth

    //Same length means it's Unicode
    return if (length1 == length2) {
      BurmeseFont.UNICODE
    } else {
      BurmeseFont.ZAWGYI
    }.also {
      detectedFont = it
    }
  }
}