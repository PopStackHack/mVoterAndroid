package com.popstack.mvoter2015.font

import android.content.Context
import android.util.AttributeSet
import android.widget.TextView
import androidx.appcompat.widget.SearchView
import androidx.core.content.res.ResourcesCompat
import com.popstack.mvoter2015.R

/**
 * An Search VIew that detects font on the user's device and set the typeface accordingly
 * This is necessary since if we use the global embedded font, zawgyi user cannot see what they type
 * It's necessary to use helpers method such as [setAutoConvertedText] and [setAutoConvertedError]
 * for the text to be converted automatically
 */
class FontOverrideSearchView @JvmOverloads constructor(
  context: Context,
  attrs: AttributeSet? = null,
  defStyleAttr: Int = 0
) : SearchView(context, attrs, defStyleAttr) {

  init {
    val typeFace = when (MDetect.identifyFont(context)) {
      BurmeseFont.ZAWGYI -> ResourcesCompat.getFont(context, R.font.zawgyi)
      BurmeseFont.UNICODE -> ResourcesCompat.getFont(context, R.font.pyidaungsu)
    }

    findViewById<TextView>(androidx.appcompat.R.id.search_src_text).typeface = typeFace
  }

}