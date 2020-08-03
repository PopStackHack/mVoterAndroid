package com.popstack.mvoter2015.feature.faq

import android.content.Context
import com.popstack.mvoter2015.R
import com.popstack.mvoter2015.domain.faq.model.FaqCategory

internal fun FaqCategory.displayString(context: Context): CharSequence {
  return when (this) {
    FaqCategory.GENERAL -> context.getString(R.string.faq_category_general)
    FaqCategory.KNOWLEDGE -> context.getString(R.string.faq_category_general)
  }
}