package com.popstack.mvoter2015.data.network.api

import com.popstack.mvoter2015.domain.candidate.model.Candidate
import com.popstack.mvoter2015.domain.candidate.model.CandidateGender
import com.popstack.mvoter2015.domain.candidate.model.CandidateId
import com.popstack.mvoter2015.domain.candidate.model.CandidateParent
import com.popstack.mvoter2015.domain.constituency.model.Constituency
import com.popstack.mvoter2015.domain.constituency.model.ConstituencyId
import com.popstack.mvoter2015.domain.constituency.model.HouseType
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

@JsonClass(generateAdapter = true)
data class CandidateListApiResponse(
  @Json(name = "data") val data: List<CandidateApiModel>
)

@JsonClass(generateAdapter = true)
data class CandidateDetailsApiResponse(
  @Json(name = "data") val data: CandidateApiModel
)

@JsonClass(generateAdapter = true)
data class CandidateApiModel(
  @Json(name = "id") val id: String,
  @Json(name = "attributes") val attributes: CandidateApiAttributes
)

@JsonClass(generateAdapter = true)
data class CandidateApiAttributes(
  @Json(name = "name") val name: String,
  @Json(name = "sorting_name") val sortingName: String,
  @Json(name = "ballot_order") val sortingBallotOrder: Long,
  @Json(name = "image") val image: String?,
  @Json(name = "birthday") val birthday: String,
  @Json(name = "age") val age: Int?,
  @Json(name = "ethnicity") val ethnicity: String,
  @Json(name = "religion") val religion: String,
  @Json(name = "education") val education: String,
  @Json(name = "gender") val gender: String,
  @Json(name = "work") val work: String?,
  @Json(name = "father") val father: ParentApiModel?,
  @Json(name = "mother") val mother: ParentApiModel?,
  @Json(name = "individual_logo") val individualLogo: String?,
  @Json(name = "party") val party: PartyApiModel?,
  @Json(name = "constituency") val constituency: ConstituencyApiResponse,
  @Json(name = "residential_address") val residentialAddress: String?,
  @Json(name = "is_ethnic_candidate") val isEthnicCandidate: Boolean,
  @Json(name = "representing_ethnicity") val representingEthnicity: String?,
  @Json(name = "is_elected") val isElected: Boolean
) {
  @JsonClass(generateAdapter = true)
  data class ParentApiModel(
    @Json(name = "name") val name: String,
    @Json(name = "ethnicity") val ethnicity: String,
    @Json(name = "religion") val religion: String
  )
}

fun CandidateApiModel.toCandidateModel(): Candidate {
  val dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.ENGLISH)

  with(attributes) {
    return Candidate(
      id = CandidateId(id),
      name = name,
      sortingName = sortingName,
      sortingBallotOrder = sortingBallotOrder,
      gender = CandidateGender.valueOf(gender),
      occupation = work ?: "",
      photoUrl = image.orEmpty(),
      education = education,
      religion = religion,
      age = age,
      birthDate = LocalDate.parse(birthday, dateTimeFormatter),
      constituency = Constituency(
        ConstituencyId(constituency.id),
        constituency.attributes.name,
        mapToHouseType(constituency.attributes.house),
        constituency.attributes.remark
      ),
      ethnicity = ethnicity,
      father = father?.run { CandidateParent(name, religion, ethnicity) },
      mother = mother?.run { CandidateParent(name, religion, ethnicity) },
      individualLogo = individualLogo,
      party = party?.mapToParty(),
      residentialAddress = residentialAddress,
      isEthnicCandidate = isEthnicCandidate,
      representingEthnicity = representingEthnicity,
      isElected = isElected
    )
  }
}

private fun mapToHouseType(house: String): HouseType {
  return when (house) {
    "amyotha" -> HouseType.UPPER_HOUSE
    "pyithu" -> HouseType.LOWER_HOUSE
    else -> HouseType.REGIONAL_HOUSE
  }
}