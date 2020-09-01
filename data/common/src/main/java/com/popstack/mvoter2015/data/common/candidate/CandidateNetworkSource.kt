package com.popstack.mvoter2015.data.common.candidate

import com.popstack.mvoter2015.domain.candidate.model.Candidate
import com.popstack.mvoter2015.domain.candidate.model.CandidateId
import com.popstack.mvoter2015.domain.constituency.model.ConstituencyId

interface CandidateNetworkSource {

  fun getCandidateList(constituencyId: ConstituencyId): List<Candidate>

  fun getCandidate(candidateId: CandidateId): Candidate

  fun searchCandidate(query: String, pageNo: Int, resultsPerPage: Int = 20): List<Candidate>

}