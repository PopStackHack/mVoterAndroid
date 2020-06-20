package com.popstack.mvoter2015.domain.faq.model

data class Faq(
  val faqId: FaqId,
  val question: String,
  val answer: String,
  val lawSource: String?,
  val articleSource: String?,
  val category: FaqCategory,
  val shareableUrl: String
)