package com.popstack.mvoter2015.feature.candidate.detail

import com.popstack.mvoter2015.domain.candidate.model.Candidate
import com.popstack.mvoter2015.domain.constituency.model.HouseType
import com.popstack.mvoter2015.domain.party.model.PartyId
import com.popstack.mvoter2015.domain.utils.BurmeseNumberUtils
import com.popstack.mvoter2015.feature.candidate.listing.SmallCandidateViewItem
import java.time.format.DateTimeFormatter
import java.util.Locale

data class CandidateDetailsViewItem(
  val candidateInfo: CandidateInfoViewItem,
  val rivals: List<SmallCandidateViewItem>
)

data class CandidateInfoViewItem(
  val photo: String,
  val name: String,
  val partyId: PartyId?,
  val partyName: String,
  val partySealImageUrl: String?,
  val houseType: String,
  val constituencyName: String,
  val age: String,
  val birthday: String,
  val education: String,
  val job: String,
  val ethnicity: String,
  val religion: String,
  val motherName: String,
  val motherEthnicity: String,
  val motherReligion: String,
  val fatherName: String,
  val fatherEthnicity: String,
  val fatherReligion: String,
  val residentialAddress: String?
)

private val dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy", Locale.ENGLISH)

fun Candidate.toCandidateInfoViewItem() = CandidateInfoViewItem(
  photo = photoUrl,
  name = name,
  partyId = party?.id,
  partyName = party?.nameBurmese ?: "တစ်သီးပုဂ္ဂလ",
  partySealImageUrl = party?.sealImage ?: individualLogo,
  // TODO: Distinguish state region
  houseType = when (constituency.house) {
    HouseType.UPPER_HOUSE -> "အမျိုးသားလွှတ်တော်"
    HouseType.LOWER_HOUSE -> "ပြည့်သူလွှတ်တော်"
    HouseType.REGIONAL_HOUSE -> "ပြည်နယ်လွှတ်တော်"
  },
  constituencyName = constituency.name,
  age = BurmeseNumberUtils.convertEnglishToBurmeseNumber(
    age?.toString()
      .orEmpty()
  )
    .toString(),
  birthday = BurmeseNumberUtils.convertEnglishToBurmeseNumber(birthDate.format(dateFormatter))
    .toString(),
  education = education,
  job = occupation,
  ethnicity = ethnicity,
  religion = religion,
  motherName = mother?.name.orEmpty(),
  motherEthnicity = mother?.ethnicity.orEmpty(),
  motherReligion = mother?.religion.orEmpty(),
  fatherName = father?.name.orEmpty(),
  fatherEthnicity = father?.ethnicity.orEmpty(),
  fatherReligion = father?.religion.orEmpty(),
  residentialAddress = residentialAddress
)