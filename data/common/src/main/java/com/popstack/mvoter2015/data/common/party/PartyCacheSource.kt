package com.popstack.mvoter2015.data.common.party

import androidx.paging.PagingSource
import com.popstack.mvoter2015.domain.party.model.Party

interface PartyCacheSource {

  fun putParty(party: Party)

  fun putParty(partyList: List<Party>)

  fun getPartyList(page: Int, itemPerPage: Int): List<Party>

  fun getPartyPaging(itemPerPage: Int): PagingSource<Int, Party>
}