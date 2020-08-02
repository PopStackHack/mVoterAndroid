package com.popstack.mvoter2015.domain.party.model

import java.time.LocalDate

data class Party(
  val id: PartyId,
  val number: Int,
  val nameBurmese: String,
  val nameEnglish: String?,
  val abbreviation: String?,
  val flagImage: String,
  val sealImage: String,
  val region: String,
  val leadersAndChairmenList: List<String>,
  val memberCount: Int?,
  val headquarterLocation: String,
  val policy: String,
  val contacts: List<String>,
  val establishmentApplicationDate: LocalDate?,
  val establishmentApprovalDate: LocalDate?,
  val registrationApplicationDate: LocalDate?,
  val registrationApprovalDate: LocalDate?
)