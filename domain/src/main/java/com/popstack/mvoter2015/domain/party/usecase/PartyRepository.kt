package com.popstack.mvoter2015.domain.party.usecase

import com.popstack.mvoter2015.domain.party.model.Party
import com.popstack.mvoter2015.domain.party.model.PartyId

interface PartyRepository {

  fun getPartyList(page: Int, itemPerPage: Int): List<Party>

  suspend fun getParty(partyId: PartyId): Party

}