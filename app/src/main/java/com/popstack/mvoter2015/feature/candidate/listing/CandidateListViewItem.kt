package com.popstack.mvoter2015.feature.candidate.listing

import com.popstack.mvoter2015.domain.candidate.model.Candidate

data class CandidateListViewItem(val candidateList: List<CandidateViewItem>)

sealed class CandidateViewItem(val id: String)

data class SmallCandidateViewItem(
  val candidateId: String,
  val name: String,
  val photoUrl: String,
  val partyName: String,
  val partySealImageUrl: String?
) : CandidateViewItem(candidateId)

data class EthnicConstituencyTitleViewItem(val value: String) : CandidateViewItem(value)

fun Candidate.toSmallCandidateViewItem() = SmallCandidateViewItem(
  candidateId = id.value,
  name = name,
  photoUrl = photoUrl,
  partyName = party?.nameBurmese ?: "တစ်သီးပုက္ကလ",
  partySealImageUrl = party?.sealImage ?: individualLogo
)