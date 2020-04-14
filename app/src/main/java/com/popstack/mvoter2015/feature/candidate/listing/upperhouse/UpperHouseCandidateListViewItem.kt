package com.popstack.mvoter2015.feature.candidate.listing.upperhouse

import com.popstack.mvoter2015.domain.candidate.model.CandidateId

data class UpperHouseCandidateListViewItem(
  val candidateId: CandidateId,
  val name: String,
  val candidateImage: String,
  val candidatePartyFlagImage: String,
  val candidatePartyName: String
)