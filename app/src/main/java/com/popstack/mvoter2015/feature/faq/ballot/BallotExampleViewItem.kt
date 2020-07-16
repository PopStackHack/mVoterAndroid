package com.popstack.mvoter2015.feature.faq.ballot

import com.popstack.mvoter2015.domain.faq.model.BallotExampleId

data class BallotExampleViewItem(
  val id: BallotExampleId,
  val image: String,
  val isValid: Boolean,
  val reason: String
)