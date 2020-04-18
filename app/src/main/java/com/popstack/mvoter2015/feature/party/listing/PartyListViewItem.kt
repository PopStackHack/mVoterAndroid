package com.popstack.mvoter2015.feature.party.listing

import com.popstack.mvoter2015.domain.party.model.PartyId

data class PartyListViewItem(
  val partyId: PartyId,
  val flagImageUrl: String,
  val name: String,
  val region: String
)