package com.popstack.mvoter2015.data.network.source

import com.popstack.mvoter2015.data.common.candidate.CandidateNetworkSource
import com.popstack.mvoter2015.data.network.api.MvoterApi
import com.popstack.mvoter2015.data.network.api.toCandidateModel
import com.popstack.mvoter2015.data.network.helper.executeOrThrow
import com.popstack.mvoter2015.domain.candidate.model.Candidate
import com.popstack.mvoter2015.domain.candidate.model.CandidateId
import com.popstack.mvoter2015.domain.constituency.model.ConstituencyId
import javax.inject.Inject

class CandidateNetworkSourceImpl @Inject constructor(
  private val mvoterApi: MvoterApi
) : CandidateNetworkSource {

  override fun getCandidateList(constituencyId: ConstituencyId): List<Candidate> {
    return mvoterApi.candidateList(constituencyId.value).executeOrThrow().data.map { it.toCandidateModel() }
  }

  override fun getCandidate(candidateId: CandidateId): Candidate {
    return mvoterApi.candidate(candidateId.value).executeOrThrow().toCandidateModel()
  }

  override fun searchCandidate(query: String, pageNo: Int, resultsPerPage: Int): List<Candidate> {
    return mvoterApi.searchCandidates(query, pageNo).executeOrThrow().map { it.toCandidateModel() }
  }

}