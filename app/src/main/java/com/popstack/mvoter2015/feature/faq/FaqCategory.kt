package com.popstack.mvoter2015.feature.faq

import android.content.Context
import com.popstack.mvoter2015.R
import com.popstack.mvoter2015.domain.faq.model.FaqCategory

internal fun FaqCategory.displayString(context: Context): CharSequence {
  return when (this) {
    FaqCategory.VOTER_LIST -> context.getString(R.string.faq_category_voter_list)
    FaqCategory.DIPLOMATIC -> context.getString(R.string.faq_category_diplomatic)
    FaqCategory.INTERNATIONAL_OBSERVER -> context.getString(R.string.faq_category_international_observer)
    FaqCategory.CANDIDATE -> context.getString(R.string.faq_category_candidate)
  }
}