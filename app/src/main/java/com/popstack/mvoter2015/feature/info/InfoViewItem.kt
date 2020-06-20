package com.popstack.mvoter2015.feature.info

import com.popstack.mvoter2015.domain.faq.model.FaqId

sealed class InfoViewItem {

  object BallotExample : InfoViewItem()

  object PollingStationProhibition : InfoViewItem()

  data class FaqViewItem(
    val faqId: FaqId,
    val question: String,
    val answer: String
  ) : InfoViewItem()
}