package com.popstack.mvoter2015.data.network.api

import com.popstack.mvoter2015.data.network.jsonadapter.LocalDateJsonAdapter
import com.popstack.mvoter2015.domain.party.model.Party
import com.popstack.mvoter2015.domain.party.model.PartyId
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.time.LocalDate
import java.time.format.DateTimeParseException

@JsonClass(generateAdapter = true)
data class GetPartyListResponse(
  @Json(name = "data") val data: List<PartyApiModel>
)

@JsonClass(generateAdapter = true)
data class GetPartyDetailResponse(
  @Json(name = "data") val data: PartyApiModel
)

@JsonClass(generateAdapter = true)
data class PartyApiModel(
  @Json(name = "id") val partyId: String,
  @Json(name = "attributes") val attributes: PartyApiAttributes
) {

  fun mapToParty(): Party {
    return Party(
      id = PartyId(partyId),
      nameBurmese = attributes.burmeseName,
      nameEnglish = attributes.englishName,
      abbreviation = attributes.abbreviation,
      flagImage = attributes.flagImageUrl,
      sealImage = attributes.sealImageUrl,
      region = attributes.region,
      leadersAndChairmenList = attributes.leadersAndChairmen,
      memberCount = attributes.memberCount,
      registeredNumber = attributes.registeredNumber,
      headquarterLocation = attributes.headQuarterLocation,
      policy = attributes.policy,
      contacts = attributes.contact,
      isEstablishedDueToArticle25 = attributes.establishmentApplicationDate == "ပုဒ်မ ၂၅ အရ လျှောက်ထားခြင်း" || attributes.establishmentApplicationDate == "ပုဒ်မ ၂၅ အရ လျှောက်ထားခြင်း",
      establishmentApplicationDate = try {
        if (attributes.establishmentApplicationDate != null)
          LocalDateJsonAdapter().fromJson(attributes.establishmentApplicationDate)
        else null
      } catch (exception: DateTimeParseException) {
        null
      },
      establishmentApprovalDate = try {
        if (attributes.establishmentApprovalDate != null)
          LocalDateJsonAdapter().fromJson(attributes.establishmentApprovalDate)
        else null
      } catch (exception: DateTimeParseException) {
        null
      },
      registrationApplicationDate = attributes.registrationApplicationDate,
      registrationApprovalDate = attributes.registrationApprovalDate
    )
  }
}

@JsonClass(generateAdapter = true)
data class PartyApiAttributes(
  @Json(name = "registered_number") val registeredNumber: Int,
  @Json(name = "name_burmese") val burmeseName: String,
  @Json(name = "name_english") val englishName: String?,
  @Json(name = "abbreviation") val abbreviation: String?,
  @Json(name = "flag_image") val flagImageUrl: String,
  @Json(name = "seal_image") val sealImageUrl: String,
  @Json(name = "region") val region: String,
  @Json(name = "leaders_and_chairmen") val leadersAndChairmen: List<String>,
  @Json(name = "member_count") val memberCount: String,
  @Json(name = "headquarter_address") val headQuarterLocation: String,
  @Json(name = "contacts") val contact: List<String>,
  @Json(name = "policy") val policy: String,
  @Json(name = "establishment_application_date") val establishmentApplicationDate: String?,
  @Json(name = "establishment_approval_date") val establishmentApprovalDate: String?,
  @Json(name = "registration_application_date") val registrationApplicationDate: LocalDate,
  @Json(name = "registration_approved_date") val registrationApprovalDate: LocalDate
)