package com.popstack.mvoter2015.data.common.party

import com.popstack.mvoter2015.domain.party.model.Party

interface PartyNetworkSource {

  fun getPartyList(page: Int, itemPerPage: Int, query: String? = null): List<Party>
}