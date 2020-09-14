package com.popstack.mvoter2015.domain.candidate.model

import com.popstack.mvoter2015.domain.constituency.model.Constituency
import com.popstack.mvoter2015.domain.party.model.Party
import java.time.LocalDate

data class Candidate(
  val id: CandidateId,
  val name: String,
  val sortingName: String,
  val gender: CandidateGender,
  val occupation: String,
  val photoUrl: String,
  val education: String,
  val religion: String,
  val age: Int?,
  val birthDate: LocalDate,
  val constituency: Constituency,
  val ethnicity: String,
  val father: CandidateParent?,
  val mother: CandidateParent?,
  val individualLogo: String?,
  val party: Party?,
  val residentialAddress: String?,
  val isEthnicCandidate: Boolean,
  val representingEthnicity: String?
)