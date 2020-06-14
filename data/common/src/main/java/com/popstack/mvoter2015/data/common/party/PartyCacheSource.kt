package com.popstack.mvoter2015.data.common.party

interface PartyCacheSource {

  fun putParty(partyEntity: PartyEntity)

  fun putParty(partyEntities: List<PartyEntity>)

  fun getPartyList(page: Int, itemPerPage: Int): List<PartyEntity>
}