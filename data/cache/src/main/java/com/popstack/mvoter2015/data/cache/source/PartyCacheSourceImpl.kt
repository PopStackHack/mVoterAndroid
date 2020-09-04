package com.popstack.mvoter2015.data.cache.source

import com.popstack.mvoter2015.data.cache.MVoterDb
import com.popstack.mvoter2015.data.cache.entity.PartyTable
import com.popstack.mvoter2015.data.cache.map.mapToParty
import com.popstack.mvoter2015.data.common.party.PartyCacheSource
import com.popstack.mvoter2015.domain.party.model.Party
import com.popstack.mvoter2015.domain.party.model.PartyId
import java.time.Clock
import java.time.LocalDateTime
import javax.inject.Inject

class PartyCacheSourceImpl @Inject constructor(
  private val db: MVoterDb,
  private val clock: Clock
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
      isEstablishedDueToArticle25 = party.isEstablishedDueToArticle25,
      establishmentApplicationDate = party.establishmentApplicationDate,
      establishmentApprovalDate = party.establishmentApprovalDate,
      registrationApplicationDate = party.registrationApplicationDate,
      registrationApprovalDate = party.registrationApprovalDate,
      lastUpdated = LocalDateTime.now(clock)
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
      offset = offset.toLong(),
      lastUpdated = getDecayTime()
    ).executeAsList().map(PartyTable::mapToParty)
  }

  override fun getParty(partyId: PartyId): Party? {
    return db.partyTableQueries.getByIdAndLastUpdated(partyId, getDecayTime()).executeAsOneOrNull()?.mapToParty()
  }

  override fun flushDecayedData() {
    return db.partyTableQueries.deleteWithLastUpdated(getDecayTime())
  }

  /**
   * Get the last updated time of party that should be accepted
   * For example, if decay is 1 hr and  the query time is 10:00 AM
   * This will returns same data with 9:00 AM
   */
  private fun getDecayTime(): LocalDateTime {
    return LocalDateTime.now(clock).minusHours(1)
  }

  override fun flush() {
    return db.partyTableQueries.deleteAll()
  }

}