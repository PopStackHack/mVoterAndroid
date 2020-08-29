package com.popstack.mvoter2015.data.cache.source

import androidx.paging.PagingSource
import com.popstack.mvoter2015.data.cache.MVoterDb
import com.popstack.mvoter2015.data.cache.entity.PartyTable
import com.popstack.mvoter2015.data.cache.extension.QueryDataSourceFactory
import com.popstack.mvoter2015.data.cache.map.mapToParty
import com.popstack.mvoter2015.data.common.party.PartyCacheSource
import com.popstack.mvoter2015.domain.party.model.Party
import com.popstack.mvoter2015.domain.party.model.PartyId
import javax.inject.Inject

class PartyCacheSourceImpl @Inject constructor(
  private val db: MVoterDb
) : PartyCacheSource {

  override fun putParty(party: Party) {
    db.partyTableQueries.insertOrReplace(
      id = party.id,
      number = party.registeredNumber,
      burmeseName = party.nameBurmese,
      englishName = party.nameEnglish,
      abbreviation = party.abbreviation,
      flagUrl = party.flagImage,
      sealUrl = party.sealImage,
      region = party.region,
      leadersAndChairmen = party.leadersAndChairmenList,
      contacts = party.contacts,
      memberCount = party.memberCount,
      headquarterLocation = party.headquarterLocation,
      policy = party.policy,
      establishmentApplicationDate = party.establishmentApplicationDate,
      establishmentApprovalDate = party.establishmentApprovalDate,
      registrationApplicationDate = party.registrationApplicationDate,
      registrationApprovalDate = party.registrationApprovalDate
    )
  }

  override fun putParty(partyList: List<Party>) {
    db.transaction {
      partyList.forEach {
        putParty(it)
      }
    }
  }

  override fun getPartyList(page: Int, itemPerPage: Int): List<Party> {
    val limit = itemPerPage
    val offset = (page - 1) * limit
    return db.partyTableQueries.getWithPage(
      limit = limit.toLong(),
      offset = offset.toLong()
    ).executeAsList().map(PartyTable::mapToParty)
  }

  override fun getPartyPaging(itemPerPage: Int): PagingSource<Int, Party> {
    return QueryDataSourceFactory(
      queryProvider = db.partyTableQueries::getWithPage,
      countQuery = db.partyTableQueries.count(),
      transacter = db.partyTableQueries
    ).map(PartyTable::mapToParty).asPagingSourceFactory().invoke()
  }

  override fun searchPartyPaging(itemPerPage: Int, query: String): PagingSource<Int, Party> {
    return QueryDataSourceFactory(
      queryProvider = { limit, offset ->
        db.partyTableQueries.searchWithPage(query, limit, offset)
      },
      countQuery = db.partyTableQueries.searchTotalCount(query),
      transacter = db.partyTableQueries
    ).map(PartyTable::mapToParty).asPagingSourceFactory().invoke()
  }

  override fun getParty(partyId: PartyId): Party? {
    return db.partyTableQueries.getById(partyId).executeAsOneOrNull()?.mapToParty()
  }

}