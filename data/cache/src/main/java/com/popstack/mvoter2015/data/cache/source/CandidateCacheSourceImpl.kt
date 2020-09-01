package com.popstack.mvoter2015.data.cache.source

import com.popstack.mvoter2015.data.cache.MVoterDb
import com.popstack.mvoter2015.data.cache.entity.CandidateWithPartyView
import com.popstack.mvoter2015.data.common.candidate.CandidateCacheSource
import com.popstack.mvoter2015.domain.candidate.model.Candidate
import com.popstack.mvoter2015.domain.candidate.model.CandidateId
import com.popstack.mvoter2015.domain.candidate.model.CandidateParent
import com.popstack.mvoter2015.domain.constituency.model.Constituency
import com.popstack.mvoter2015.domain.constituency.model.ConstituencyId
import com.popstack.mvoter2015.domain.constituency.model.HouseType
import com.popstack.mvoter2015.domain.party.model.Party
import javax.inject.Inject

class CandidateCacheSourceImpl @Inject constructor(
  private val db: MVoterDb
) : CandidateCacheSource {

  override fun putCandidate(candidate: Candidate) {
    insertOrReplaceParty(candidate.party)
    insertOrReplaceCandidate(candidate)
  }

  override fun putCandidateList(candidateList: List<Candidate>) {
    db.transaction {
      candidateList.map { putCandidate(it) }
    }
  }

  override fun getCandidateList(constituencyId: ConstituencyId): List<Candidate> {
    return db.candidateWithPartyViewQueries.getCandidateList(constituencyId).executeAsList().map {
      it.toCandidateModel()
    }
  }

  override fun getCandidate(candidateId: CandidateId): Candidate {
    return db.candidateWithPartyViewQueries.getCandidateById(candidateId).executeAsOne().toCandidateModel()
  }

  private fun insertOrReplaceParty(party: Party) {
    db.partyTableQueries.insertOrReplace(
      id = party.id,
      number = party.registeredNumber,
      burmeseName = party.nameBurmese,
      englishName = party.nameEnglish,
      abbreviation = party.abbreviation,
      flagUrl = party.flagImage,
      sealUrl = party.sealImage,
      region = party.region,
      leadersAndChairmen = party.leadersAndChairmenList,
      contacts = party.contacts,
      memberCount = party.memberCount,
      headquarterLocation = party.headquarterLocation,
      policy = party.policy,
      isEstablishedDueToArticle25 = party.isEstablishedDueToArticle25,
      establishmentApplicationDate = party.establishmentApplicationDate,
      establishmentApprovalDate = party.establishmentApprovalDate,
      registrationApplicationDate = party.registrationApplicationDate,
      registrationApprovalDate = party.registrationApprovalDate
    )
  }

  private fun insertOrReplaceCandidate(candidate: Candidate) = with(candidate) {
    db.candidateTableQueries.insertOrReplace(
      id = id,
      name = name,
      gender = gender,
      occupation = occupation,
      photoUrl = photoUrl,
      education = education,
      religion = religion,
      age = age?.toLong(),
      birthDate = birthDate,
      ethnicity = ethnicity,
      fatherName = father?.name,
      fatherReligion = father?.religion,
      motherName = mother?.name,
      motherReligion = mother?.religion,
      partyId = party.id,
      constituencyId = ConstituencyId(constituency.id),
      constituencyName = constituency.name
    )
  }
}

fun CandidateWithPartyView.toCandidateModel() = Candidate(
  id = id,
  name = name,
  gender = gender,
  occupation = occupation,
  photoUrl = photoUrl,
  education = education,
  religion = religion,
  age = age?.toInt(),
  birthDate = birthDate,
  constituency = Constituency(
    // TODO: Change house accordingly
    id = "",
    name = constituencyName,
    house = HouseType.LOWER_HOUSE
  ),
  ethnicity = ethnicity,
  father = CandidateParent(
    name = fatherName.orEmpty(),
    religion = fatherReligion.orEmpty(),
    ""
  ),
  mother = CandidateParent(
    name = motherName.orEmpty(),
    religion = motherReligion.orEmpty(),
    ""
  ),
  party = Party(
    id = partyId,
    registeredNumber = partyNumber,
    nameBurmese = partyBurmeseName,
    nameEnglish = partyEnglishName,
    abbreviation = partyAbbreviation,
    flagImage = partyFlagImage,
    sealImage = partySealImage,
    region = partyRegion,
    leadersAndChairmenList = partyLeadersAndChairmen,
    memberCount = partyMemberCount,
    headquarterLocation = partyHeadquarterLocation,
    policy = partyPolicy,
    contacts = partyContacts,
    establishmentApplicationDate = partyEstablishmentApplicationDate,
    establishmentApprovalDate = partyEstablishmentApprovalDate,
    registrationApplicationDate = partyRegistrationApplicationDate,
    registrationApprovalDate = partyRegistrationApprovalDate,
    isEstablishedDueToArticle25 = partyIsEstablishedDueToArticle25
  )
)