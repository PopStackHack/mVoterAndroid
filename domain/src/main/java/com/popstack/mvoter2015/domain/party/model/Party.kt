package com.popstack.mvoter2015.domain.party.model

import java.time.LocalDate

data class Party(
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
)