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
      number = party.number,
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
    ).executeAsList().map(PartyTable::mapToEntity)
  }

}

fun PartyTable.mapToEntity(): Party {
  return Party(
    id = id,
    number = number,
    nameBurmese = burmeseName,
    nameEnglish = englishName,
    abbreviation = abbreviation,
    flagImage = flagImage,
    sealImage = sealImage,
    region = region,
    leadersAndChairmenList = leadersAndChairmen,
    contacts = contacts,
    memberCount = memberCount,
    headquarterLocation = headquarterLocation,
    policy = policy,
    establishmentApplicationDate = establishmentApplicationDate,
    establishmentApprovalDate = establishmentApprovalDate,
    registrationApplicationDate = registrationApplicationDate,
    registrationApprovalDate = registrationApprovalDate
  )
}