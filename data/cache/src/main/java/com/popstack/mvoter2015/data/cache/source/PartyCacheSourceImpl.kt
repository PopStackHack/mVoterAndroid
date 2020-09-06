package com.popstack.mvoter2015.data.cache.source

import com.popstack.mvoter2015.data.cache.MVoterDb
import com.popstack.mvoter2015.data.cache.entity.PartyTable
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
      id = party.id.value,
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
      isEstablishedDueToArticle25 = party.isEstablishedDueToArticle25,
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
    return db.partyTableQueries.getAll(
      limit = limit.toLong(),
      offset = offset.toLong(),
    ).executeAsList().map(PartyTable::mapToParty)
  }

  override fun getParty(partyId: PartyId): Party? {
    return db.partyTableQueries.getById(partyId.value).executeAsOneOrNull()?.mapToParty()
  }

  override fun flush() {
    return db.partyTableQueries.deleteAll()
  }

}