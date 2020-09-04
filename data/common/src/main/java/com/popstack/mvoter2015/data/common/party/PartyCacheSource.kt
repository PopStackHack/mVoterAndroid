package com.popstack.mvoter2015.data.common.party

import androidx.paging.PagingSource
import com.popstack.mvoter2015.domain.party.model.Party
import com.popstack.mvoter2015.domain.party.model.PartyId

interface PartyCacheSource {

  fun putParty(party: Party)

  fun putParty(partyList: List<Party>)

  fun getPartyList(page: Int, itemPerPage: Int): List<Party>

  fun getParty(partyId: PartyId): Party?

  fun wipe()
}