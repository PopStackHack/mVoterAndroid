package com.popstack.mvoter2015.data.common.party

import com.popstack.mvoter2015.domain.party.model.Party
import com.popstack.mvoter2015.domain.party.usecase.PartyRepository
import javax.inject.Inject

class PartyRepositoryImpl @Inject constructor(
  private val partyCacheSource: PartyCacheSource,
  private val partyNetworkSource: PartyNetworkSource
) : PartyRepository {

  override fun getPartyList(page: Int, itemPerPage: Int): List<Party> {

    try {
      val partyListFromNetwork = partyNetworkSource.getPartyList(page, itemPerPage)
      partyCacheSource.putParty(partyListFromNetwork)
    } catch (exception: Exception) {
      //Network error, see if can recover from cache
      val partyListFromCache = partyCacheSource.getPartyList(page, itemPerPage)
      if (partyListFromCache.isEmpty()) {
        //Seems data is empty, can't recover, throw error
        throw exception
      }
      return partyListFromCache
    }

    //We use database as single source of truth
    return partyCacheSource.getPartyList(page, itemPerPage)
  }

}