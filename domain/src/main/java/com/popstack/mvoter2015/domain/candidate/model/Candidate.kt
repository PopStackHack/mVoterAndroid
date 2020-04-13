package com.popstack.mvoter2015.domain.candidate.model

import com.popstack.mvoter2015.domain.party.PartyId
import java.time.LocalDate

data class Candidate(
  val id: CandidateId,
  val name: String,
  val gender: CandidateGender,
  val occupation: String,
  val photoUrl: String,
  val votes: Int,
  val education: String,
  val legislature: String,
  val religion: String,
  val birthDate: LocalDate,
  val wardVillage: String,
  val ethnicity: String,
  val father: CandidateParent?,
  val mother: CandidateParent?,
  val partyId: PartyId
)
//@Nullable public String mpid;
//public String independent_logo;
//public Constituency constituency;