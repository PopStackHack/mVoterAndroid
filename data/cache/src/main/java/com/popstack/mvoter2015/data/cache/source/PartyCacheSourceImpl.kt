package com.popstack.mvoter2015.data.cache.source

import com.popstack.mvoter2015.data.cache.MVoterDb
import com.popstack.mvoter2015.data.cache.entity.PartyTable
import com.popstack.mvoter2015.data.common.party.PartyCacheSource
import com.popstack.mvoter2015.data.common.party.PartyEntity
import javax.inject.Inject

class PartyCacheSourceImpl @Inject constructor(
  private val db: MVoterDb
) : PartyCacheSource {

  override fun putParty(partyEntity: PartyEntity) {
    db.partyTableQueries.insertOrReplace(
      id = partyEntity.id,
      englishName = partyEntity.englishName,
      burmeseName = partyEntity.burmeseName,
      abbreviation = partyEntity.abbreviation,
      flagUrl = partyEntity.flagUrl,
      sealUrl = partyEntity.sealUrl,
      region = partyEntity.region,
      chairmans = partyEntity.chairmanList,
      leaders = partyEntity.leaderList,
      memberCount = partyEntity.memberCount,
      headquarterLocation = partyEntity.headquarterLocation,
      policy = partyEntity.policy,
      registrationApplicationDate = partyEntity.registrationApplicationDate,
      registrationApprovalDate = partyEntity.registrationApprovalDate,
      establishmentApprovalDate = partyEntity.establishmentApprovalDate
    )
  }

  override fun putParty(partyEntities: List<PartyEntity>) {
    db.transaction {
      partyEntities.forEach {
        putParty(it)
      }
    }
  }

  override fun getPartyList(page: Int, itemPerPage: Int): List<PartyEntity> {
    val limit = itemPerPage
    val offset = (page - 1) * limit
    return db.partyTableQueries.getWithPage(
      limit = limit.toLong(),
      offset = offset.toLong()
    ).executeAsList().map(PartyTable::mapToEntity)
  }

}

fun PartyTable.mapToEntity(): PartyEntity {
  return PartyEntity(
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