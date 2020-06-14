package com.popstack.mvoter2015.data.common.party

import com.popstack.mvoter2015.domain.party.model.Party
import com.popstack.mvoter2015.domain.party.model.PartyId
import java.time.LocalDate

data class PartyEntity(
  val id: PartyId,
  val englishName: String,
  val burmeseName: String,
  val abbreviation: String?,
  val flagUrl: String,
  val sealUrl: String,
  val region: String,
  val chairmanList: List<String>,
  val leaderList: List<String>,
  val memberCount: Int?,
  val headquarterLocation: String?,
  val policy: String?,
  val registrationApplicationDate: LocalDate?,
  val registrationApprovalDate: LocalDate?,
  val establishmentApprovalDate: LocalDate?
) {

  fun mapToDomainModel(): Party {
    return Party(
      id = id,
      englishName = englishName,
      burmeseName = burmeseName,
      abbreviation = abbreviation,
      flagUrl = flagUrl,
      sealUrl = sealUrl,
      region = region,
      chairmanList = chairmanList,
      leaderList = leaderList,
      memberCount = memberCount,
      headquarterLocation = headquarterLocation,
      policy = policy,
      registrationApplicationDate = registrationApplicationDate,
      registrationApprovalDate = registrationApprovalDate,
      establishmentApprovalDate = establishmentApprovalDate
    )
  }

}