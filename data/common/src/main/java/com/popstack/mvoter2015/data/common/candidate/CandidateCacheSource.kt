package com.popstack.mvoter2015.data.common.candidate

import com.popstack.mvoter2015.domain.candidate.model.Candidate
import com.popstack.mvoter2015.domain.candidate.model.CandidateId
import com.popstack.mvoter2015.domain.constituency.model.ConstituencyId

interface CandidateCacheSource {
  fun putCandidate(candidate: Candidate)

  fun putCandidateList(candidateList: List<Candidate>, queryConstituencyId: ConstituencyId)

  fun getCandidateList(constituencyId: ConstituencyId): List<Candidate>

  fun getCandidate(candidateId: CandidateId): Candidate?

  fun flushUnderConstituency(constituencyId: ConstituencyId)
}