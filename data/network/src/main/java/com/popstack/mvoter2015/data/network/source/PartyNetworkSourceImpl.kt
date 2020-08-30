package com.popstack.mvoter2015.data.network.source

import com.popstack.mvoter2015.data.common.party.PartyNetworkSource
import com.popstack.mvoter2015.data.network.api.MvoterApi
import com.popstack.mvoter2015.data.network.api.PartyApiModel
import com.popstack.mvoter2015.data.network.helper.executeOrThrow
import com.popstack.mvoter2015.domain.party.model.Party
import com.popstack.mvoter2015.domain.party.model.PartyId
import javax.inject.Inject

class PartyNetworkSourceImpl @Inject constructor(
  private val mvoterApi: MvoterApi
) : PartyNetworkSource {

  override fun getPartyList(page: Int, itemPerPage: Int, query: String?): List<Party> {
    return mvoterApi.partyList(page, query).executeOrThrow().data.map(PartyApiModel::mapToParty)
  }

  override fun getParty(input: PartyId): Party {
    return mvoterApi.party(input.value).executeOrThrow().data.mapToParty()
  }

}