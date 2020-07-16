package com.popstack.mvoter2015.domain.faq.model

data class BallotExample(
  val id: BallotExampleId,
  val image: String,
  val isValid: Boolean,
  val reason: String
)