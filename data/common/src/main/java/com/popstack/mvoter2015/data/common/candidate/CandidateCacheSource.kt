package com.popstack.mvoter2015.data.common.candidate

import com.popstack.mvoter2015.domain.candidate.model.Candidate
import com.popstack.mvoter2015.domain.candidate.model.CandidateId
import com.popstack.mvoter2015.domain.constituency.model.ConstituencyId
import com.popstack.mvoter2015.domain.constituency.model.HouseType

interface CandidateCacheSource {
  fun putCandidate(candidate: Candidate)

  fun putCandidateList(candidateList: List<Candidate>)

  fun getCandidateList(constituencyId: ConstituencyId): List<Candidate>

  fun getCandidate(candidateId: CandidateId): Candidate
}