package com.popstack.mvoter2015.feature.faq

import android.content.Context
import com.popstack.mvoter2015.R
import com.popstack.mvoter2015.domain.faq.model.BallotExampleCategory

internal fun BallotExampleCategory.displayString(context: Context): CharSequence {
  return when (this) {
    BallotExampleCategory.NORMAL -> context.getString(R.string.ballot_category_normal)
    BallotExampleCategory.ADVANCED -> context.getString(R.string.ballot_category_advanced)
  }
}