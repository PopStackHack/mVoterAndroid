package com.popstack.mvoter2015.data.network.api

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.time.LocalDate

@JsonClass(generateAdapter = true)
data class PartyApiModel(
  @Json(name = "id") val id: String,
  @Json(name = "name_english") val englishName: String,
  @Json(name = "name_burmese") val burmeseName: String,
  @Json(name = "abbreviation") val abbreviation: String,
  @Json(name = "flag_image") val flagImageUrl: String,
  @Json(name = "seal_image") val sealImageUrl: String,
  @Json(name = "region") val region: String,
  @Json(name = "chairmans") val chairmans: List<String>,
  @Json(name = "leaders") val leaders: List<String>,
  @Json(name = "member_count") val memberCount: Int,
  @Json(name = "headquarter_location") val headQuarterLocation: String?,
  @Json(name = "contact_number") val contact: String?,
  @Json(name = "policy") val policy: String?,
  @Json(name = "registration_application_date") val registrationApplicationDate: LocalDate?,
  @Json(name = "registration_approved_date") val registrationApprovalDate: LocalDate,
  @Json(name = "establishment_approval_date") val establishmentApprovalDate: LocalDate?,
  @Json(name = "establishment_date") val establishmentDate: LocalDate?
)