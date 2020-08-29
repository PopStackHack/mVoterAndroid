package com.popstack.mvoter2015.feature.faq

import com.popstack.mvoter2015.domain.faq.model.FaqId

sealed class FaqViewItem {

  object BallotExample : FaqViewItem()

  object PollingStationProhibition : FaqViewItem()

  data class QuestionAndAnswer(
    val faqId: FaqId,
    val question: String,
    val answer: String,
    val source: String?
  ) : FaqViewItem()
}