package com.popstack.mvoter2015.feature.candidate.listing

import com.popstack.mvoter2015.domain.candidate.model.Candidate

data class CandidateListViewItem(val candidateList: List<SmallCandidateViewItem>) {
  data class SmallCandidateViewItem(
    val id: String,
    val name: String,
    val photoUrl: String,
    val partyName: String,
    val partyImageUrl: String
  )
}

fun Candidate.toSmallCandidateViewItem() = CandidateListViewItem.SmallCandidateViewItem(
  id = id.value,
  name = name,
  photoUrl = photoUrl,
  partyName = party.nameBurmese,
  partyImageUrl = party.sealImage
)