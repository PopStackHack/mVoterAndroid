package com.popstack.mvoter2015.data.cache.source

import com.popstack.mvoter2015.data.cache.MVoterDb
import com.popstack.mvoter2015.data.cache.entity.PartyTable
import com.popstack.mvoter2015.data.common.party.PartyCacheSource
import com.popstack.mvoter2015.domain.party.model.Party
import javax.inject.Inject

class PartyCacheSourceImpl @Inject constructor(
  private val db: MVoterDb
) : PartyCacheSource {

  override fun putParty(party: Party) {
    db.partyTableQueries.insertOrReplace(
      id = party.id,
      englishName = party.englishName,
      burmeseName = party.burmeseName,
      abbreviation = party.abbreviation,
      flagUrl = party.flagUrl,
      sealUrl = party.sealUrl,
      region = party.region,
      chairmans = party.chairmanList,
      leaders = party.leaderList,
      memberCount = party.memberCount,
      headquarterLocation = party.headquarterLocation,
      policy = party.policy,
      registrationApplicationDate = party.registrationApplicationDate,
      registrationApprovalDate = party.registrationApprovalDate,
      establishmentApprovalDate = party.establishmentApprovalDate
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
    ).executeAsList().map(PartyTable::mapToEntity)
  }

}

fun PartyTable.mapToEntity(): Party {
  return Party(
    id = id,
    englishName = englishName,
    burmeseName = burmeseName,
    abbreviation = abbreviation,
    flagUrl = flagUrl,
    sealUrl = sealUrl,
    region = region,
    chairmanList = chairmans,
    leaderList = leaders,
    memberCount = memberCount,
    headquarterLocation = headquarterLocation,
    policy = policy,
    registrationApplicationDate = registrationApplicationDate,
    registrationApprovalDate = registrationApprovalDate,
    establishmentApprovalDate = establishmentApprovalDate
  )
}