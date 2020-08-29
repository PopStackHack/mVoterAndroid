package com.popstack.mvoter2015.feature.party.detail

import com.popstack.mvoter2015.domain.party.model.PartyId
import com.popstack.mvoter2015.domain.utils.convertToBurmeseNumber
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

data class PartyDetailViewItem(
  val partyId: PartyId,
  val name: String,
  val nameEnglish: String?,
  val nameAbbreviation: String?,
  val region: String,
  val policy: String,
  val flagImage: String,
  val sealImage: String,
  val partyNumber: String,
  val leadersAndChairmen: String,
  val memberCount: String,
  val headQuarterLocation: String,
  val contact: String,
  val isPoteMa25: Boolean,
  val timeline: List<PartyTimelineViewItem>
)

data class PartyTimelineViewItem(
  val date: LocalDate,
  val event: TimelineEvent
) {

  fun presentableDate(): String {
    return DateTimeFormatter.ofPattern("dd-MM'\n'yyyy", Locale.ENGLISH).format(date).convertToBurmeseNumber()
  }
}

enum class TimelineEvent {
  ESTABLISHMENT_APPLICATION,
  ESTABLISHMENT_APPROVAL,
  REGISTRATION_APPLICATION,
  REGISTRATION_APPROVAL
}