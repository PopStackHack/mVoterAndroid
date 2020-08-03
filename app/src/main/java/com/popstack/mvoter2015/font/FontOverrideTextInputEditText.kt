package com.popstack.mvoter2015.font

import android.content.Context
import android.util.AttributeSet
import androidx.annotation.StringRes
import androidx.core.content.res.ResourcesCompat
import com.google.android.material.textfield.TextInputEditText
import com.popstack.mvoter2015.R

/**
 * An Edit Text that detects font on the user's device and set the typeface accordingly
 * This is necessary since if we use the global embedded font, zawgyi user cannot see what they type
 * It's necessary to use helpers method such as [setAutoConvertedText] and [setAutoConvertedError]
 * for the text to be converted automatically
 */
class FontOverrideTextInputEditText @JvmOverloads constructor(
  context: Context,
  attrs: AttributeSet? = null,
  defStyleAttr: Int = R.attr.editTextStyle
) : TextInputEditText(context, attrs, defStyleAttr) {

  init {
    val typeFace = when (MDetect.identifyFont(context)) {
      BurmeseFont.ZAWGYI -> ResourcesCompat.getFont(context, R.font.zawgyi)
      BurmeseFont.UNICODE -> ResourcesCompat.getFont(context, R.font.pyidaungsu)
    }
    typeface = typeFace
  }

  fun setAutoConvertedText(@StringRes error: Int) {
    setAutoConvertedText(context.getString(error))
  }

  fun setAutoConvertedText(errorString: String) {
    if (MDetect.identifyFont(context) == BurmeseFont.ZAWGYI) {
      setText(Rabbit.uni2zg(errorString))
    } else {
      setText(errorString)
    }
  }

  fun setAutoConvertedError(@StringRes error: Int) {
    setAutoConvertedError(context.getString(error))
  }

  fun setAutoConvertedError(errorString: String) {
    if (MDetect.identifyFont(context) == BurmeseFont.ZAWGYI) {
      error = Rabbit.uni2zg(errorString)
    } else {
      error = errorString
    }
  }

  fun setAutoConvertedHint(@StringRes hint: Int) {
    setAutoConvertedError(context.getString(hint))
  }

  fun setAutoConvertedHint(hintString: String) {
    if (MDetect.identifyFont(context) == BurmeseFont.ZAWGYI) {
      hint = Rabbit.uni2zg(hintString)
    } else {
      hint = hintString
    }
  }

}