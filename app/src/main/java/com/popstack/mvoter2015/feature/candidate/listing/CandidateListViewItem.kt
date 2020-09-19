package com.popstack.mvoter2015.feature.candidate.listing

import com.popstack.mvoter2015.domain.candidate.model.Candidate

sealed class CandidateListResult {

  data class Remark(val remarkMessage: String) : CandidateListResult()

  data class CandidateListViewItem(val candidateList: List<CandidateViewItem>) : CandidateListResult()
}

sealed class CandidateViewItem(val id: String)

data class SmallCandidateViewItem(
  val candidateId: String,
  val name: String,
  val photoUrl: String,
  val partyName: String,
  val partySealImageUrl: String?
) : CandidateViewItem(candidateId)

data class CandidateSectionTitleViewItem(val value: String) : CandidateViewItem(value)

fun Candidate.toSmallCandidateViewItem() = SmallCandidateViewItem(
  candidateId = id.value,
  name = name,
  photoUrl = photoUrl,
  partyName = party?.nameBurmese ?: "တစ်သီးပုဂ္ဂလ",
  partySealImageUrl = party?.sealImage ?: individualLogo
)