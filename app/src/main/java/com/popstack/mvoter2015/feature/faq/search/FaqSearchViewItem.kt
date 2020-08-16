package com.popstack.mvoter2015.feature.faq.search

import com.popstack.mvoter2015.domain.faq.model.FaqId

data class FaqSearchViewItem(
  val faqId: FaqId,
  val question: String,
  val answer: String
)