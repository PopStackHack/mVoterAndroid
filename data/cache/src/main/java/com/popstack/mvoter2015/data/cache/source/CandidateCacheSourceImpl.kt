package com.popstack.mvoter2015.data.cache.source

import com.popstack.mvoter2015.data.cache.MVoterDb
import com.popstack.mvoter2015.data.common.candidate.CandidateCacheSource
import com.popstack.mvoter2015.data.common.party.PartyCacheSource
import com.popstack.mvoter2015.domain.candidate.model.Candidate
import com.popstack.mvoter2015.domain.candidate.model.CandidateGender
import com.popstack.mvoter2015.domain.candidate.model.CandidateId
import com.popstack.mvoter2015.domain.candidate.model.CandidateParent
import com.popstack.mvoter2015.domain.constituency.model.Constituency
import com.popstack.mvoter2015.domain.constituency.model.ConstituencyId
import com.popstack.mvoter2015.domain.constituency.model.HouseType
import com.popstack.mvoter2015.domain.party.model.Party
import com.popstack.mvoter2015.domain.party.model.PartyId
import java.time.LocalDate
import javax.inject.Inject

class CandidateCacheSourceImpl @Inject constructor(
  private val db: MVoterDb,
  private val partyCacheSource: PartyCacheSource
) : CandidateCacheSource {

  override fun putCandidate(candidate: Candidate) {
    db.candidateTableQueries.transaction {
      candidate.party?.let(partyCacheSource::putParty)
      with(candidate.constituency) {
        db.constitutencyTableQueries.insertOrReplace(
          id = id.value,
          name = name,
          house = house,
          remark = remark
        )
      }

      if (db.candidateTableQueries.getById(candidate.id.value).executeAsOneOrNull() != null) {
        //Exists update
        with(candidate) {
          db.candidateTableQueries.update(
            id = id.value,
            name = name,
            sortingName = sortingName,
            sortingBallotOrder = sortingBallotOrder,
            gender = gender,
            occupation = occupation,
            photoUrl = photoUrl,
            education = education,
            religion = religion,
            age = age?.toLong(),
            birthDate = birthDate,
            ethnicity = ethnicity,
            father = father,
            mother = mother,
            individualLogo = individualLogo,
            isEthnicCandidate = isEthnicCandidate,
            representingEthnicity = representingEthnicity,
            residentalAddress = residentialAddress,
            partyId = party?.id,
            constituencyId = constituency.id.value
          )
        }
      } else {
        //Doesn't exist, add new
        insertOrReplaceCandidate(candidate, null)
      }
    }
  }

  override fun putRivalCandidateList(candidateList: List<Candidate>, queryConstituencyId: ConstituencyId) {
    db.candidateTableQueries.transaction {
      candidateList.forEach { candidate ->
        db.candidateTableQueries.transaction {
          candidate.party?.let(partyCacheSource::putParty)
          with(candidate.constituency) {
            db.constitutencyTableQueries.insertOrReplace(
              id = id.value,
              name = name,
              house = house,
              remark = remark
            )
          }

          if (db.candidateTableQueries.getById(candidate.id.value).executeAsOneOrNull() != null) {
            //Exists update
            with(candidate) {
              db.candidateTableQueries.update(
                id = id.value,
                name = name,
                sortingName = sortingName,
                sortingBallotOrder = sortingBallotOrder,
                gender = gender,
                occupation = occupation,
                photoUrl = photoUrl,
                education = education,
                religion = religion,
                age = age?.toLong(),
                birthDate = birthDate,
                ethnicity = ethnicity,
                father = father,
                mother = mother,
                individualLogo = individualLogo,
                isEthnicCandidate = isEthnicCandidate,
                representingEthnicity = representingEthnicity,
                residentalAddress = residentialAddress,
                partyId = party?.id,
                constituencyId = constituency.id.value
              )
            }
          } else {
            //Doesn't exist, add new
            insertOrReplaceCandidate(candidate, null)
          }
        }
      }
    }
  }

  private fun insertOrReplaceCandidate(candidate: Candidate, queryConstituencyId: ConstituencyId?) {
    with(candidate) {
      db.candidateTableQueries.insertOrReplace(
        id = id.value,
        name = name,
        sortingName = sortingName,
        sortingBallotOrder = sortingBallotOrder,
        gender = gender,
        occupation = occupation,
        photoUrl = photoUrl,
        education = education,
        religion = religion,
        age = age?.toLong(),
        birthDate = birthDate,
        ethnicity = ethnicity,
        father = father,
        mother = mother,
        individualLogo = individualLogo,
        isEthnicCandidate = isEthnicCandidate,
        representingEthnicity = representingEthnicity,
        residentalAddress = residentialAddress,
        partyId = party?.id,
        constituencyId = constituency.id.value,
        queryConstituencyid = queryConstituencyId?.value,
        isElected = isElected
      )
    }
  }

  override fun putCandidateList(candidateList: List<Candidate>, queryConstituencyId: ConstituencyId) {
    db.transaction {
      candidateList.forEach { candidate ->
        candidate.party?.let(partyCacheSource::putParty)
        with(candidate.constituency) {
          db.constitutencyTableQueries.insertOrReplace(
            id = id.value,
            name = name,
            house = house,
            remark = remark
          )
        }
        insertOrReplaceCandidate(candidate, queryConstituencyId)
      }
    }
  }

  private val candidateMapper: (
    id: String,
    name: String,
    sortingName: String,
    sortingBallotOrder: Long,
    gender: CandidateGender,
    occupation: String,
    photoUrl: String,
    education: String,
    religion: String,
    age: Long?,
    birthDate: LocalDate?,
    ethnicity: String,
    father: CandidateParent?,
    mother: CandidateParent?,
    individualLogo: String?,
    residentalAddress: String?,
    isEthnicCandidate: Boolean,
    representingEthnicity: String?,
    partyId: PartyId?,
    constituencyId: String,
    queryConstituencyId: String?,
    isElected: Boolean,
    _constituencyId: String,
    _constituencyName: String,
    _constituencyHouse: HouseType,
    _constituencyRemark: String?,
    _partyId: String?,
    partyNumber: Int?,
    partyBurmeseName: String?,
    partyEnglishName: String?,
    partyAbbreviation: String?,
    partyFlagImage: String?,
    partySealImage: String?,
    partyRegion: String?,
    partyLeadersAndChairmen: List<String>?,
    partyMemberCount: String?,
    partyContacts: List<String>?,
    partyHeadquarterLocation: String?,
    partyPolicy: String?,
    partyEstablishmentApplicationDate: LocalDate?,
    partyEstablishmentApprovalDate: LocalDate?,
    partyRegistrationApplicationDate: LocalDate?,
    partyRegistrationApprovalDate: LocalDate?,
    partyIsEstablishedDueToArticle25: Boolean?
  ) -> Candidate =
    { id, name, sortingName, sortingBallotOrder,
      gender, occupation, photoUrl, education, religion, age, birthDate, ethnicity, father, mother,
      individualLogo, residentalAddress, isEthnicCandidate, representingEthnicity, partyId,
      constituencyId, queryConstituencyId, isElected, _constituencyId, _constituencyName, _constituencyHouse,
      _constituencyRemark, _partyId, partyNumber, partyBurmeseName, partyEnglishName,
      partyAbbreviation, partyFlagImage, partySealImage, partyRegion, partyLeadersAndChairmen,
      partyMemberCount, partyContacts, partyHeadquarterLocation, partyPolicy,
      partyEstablishmentApplicationDate, partyEstablishmentApprovalDate,
      partyRegistrationApplicationDate, partyRegistrationApprovalDate,
      partyIsEstablishedDueToArticle25 ->

      Candidate(
        id = CandidateId(id),
        name = name,
        sortingName = sortingName,
        sortingBallotOrder = sortingBallotOrder,
        gender = gender,
        occupation = occupation,
        photoUrl = photoUrl,
        education = education,
        religion = religion,
        age = age?.toInt(),
        birthDate = birthDate,
        constituency = Constituency(
          id = ConstituencyId(_constituencyId),
          name = _constituencyName,
          house = _constituencyHouse,
          remark = _constituencyRemark
        ),
        ethnicity = ethnicity,
        father = father,
        mother = mother,
        individualLogo = individualLogo,
        residentialAddress = residentalAddress,
        isEthnicCandidate = isEthnicCandidate,
        representingEthnicity = representingEthnicity,
        isElected = isElected,
        party = if (partyId != null) {
          Party(
            id = partyId,
            registeredNumber = partyNumber!!,
            nameBurmese = partyBurmeseName!!,
            nameEnglish = partyEnglishName,
            abbreviation = partyAbbreviation,
            flagImage = partyFlagImage!!,
            sealImage = partySealImage!!,
            region = partyRegion!!,
            leadersAndChairmenList = partyLeadersAndChairmen!!,
            memberCount = partyMemberCount,
            headquarterLocation = partyHeadquarterLocation!!,
            policy = partyPolicy!!,
            contacts = partyContacts!!,
            establishmentApplicationDate = partyEstablishmentApplicationDate,
            establishmentApprovalDate = partyEstablishmentApprovalDate,
            registrationApplicationDate = partyRegistrationApplicationDate,
            registrationApprovalDate = partyRegistrationApprovalDate,
            isEstablishedDueToArticle25 = partyIsEstablishedDueToArticle25!!
          )
        } else {
          null
        }
      )
    }

  override fun getCandidateList(constituencyId: ConstituencyId): List<Candidate> {
    return db.candidateWithConstituencyViewQueries.getByConstituency(
      constituencyId.value, candidateMapper
    )
      .executeAsList()
  }

  override fun getRivalCandidateList(constituencyId: ConstituencyId): List<Candidate> {
    return db.candidateWithConstituencyViewQueries.getByActualConstituency(
      constituencyId.value, candidateMapper
    )
      .executeAsList()
  }

  override fun getCandidate(candidateId: CandidateId): Candidate? {
    return db.candidateWithConstituencyViewQueries.getById(candidateId.value, candidateMapper)
      .executeAsOneOrNull()
  }

  override fun flushUnderConstituency(constituencyId: ConstituencyId) {
    db.candidateTableQueries.deleteByConstituency(constituencyId.value)
  }

}