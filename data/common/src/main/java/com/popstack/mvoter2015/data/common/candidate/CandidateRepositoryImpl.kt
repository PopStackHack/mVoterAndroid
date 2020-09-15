package com.popstack.mvoter2015.data.common.candidate

import com.popstack.mvoter2015.domain.candidate.CandidateRepository
import com.popstack.mvoter2015.domain.candidate.model.Candidate
import com.popstack.mvoter2015.domain.candidate.model.CandidateId
import com.popstack.mvoter2015.domain.constituency.model.ConstituencyId
import com.popstack.mvoter2015.domain.exception.NetworkException
import javax.inject.Inject

class CandidateRepositoryImpl @Inject constructor(
  private val candidateCacheSource: CandidateCacheSource,
  private val candidateNetworkSource: CandidateNetworkSource
) : CandidateRepository {

  override suspend fun getCandidateList(constituencyId: ConstituencyId): List<Candidate> {
    try {
      val candidateListFromNetwork = candidateNetworkSource.getCandidateList(constituencyId)
      candidateCacheSource.flushUnderConstituency(constituencyId)
      candidateCacheSource.putCandidateList(candidateListFromNetwork)
    } catch (exception: NetworkException) {
      //Network error, see if can recover from cache
      if (exception.errorCode == 404) {
        //Don't recover from 404
        throw exception
      }
      val candidateListFromCache = candidateCacheSource.getCandidateList(constituencyId)
      if (candidateListFromCache.isEmpty()) {
        //Seems data is empty, can't recover, throw error
        throw exception
      }
      return candidateListFromCache
    }

    //We use database as single source of truth
    return candidateCacheSource.getCandidateList(constituencyId)
  }

  override fun getCandidate(candidateId: CandidateId): Candidate {
    try {
      val candidateFromNetwork = candidateNetworkSource.getCandidate(candidateId)
      candidateCacheSource.putCandidate(candidateFromNetwork)
      return candidateCacheSource.getCandidate(candidateId)!!
    } catch (exception: Exception) {
      return candidateCacheSource.getCandidate(candidateId) ?: throw exception
    }
  }

  override fun searchCandidate(
    query: String,
    pageNo: Int,
    resultPerPage: Int
  ): List<Candidate> {
    return candidateNetworkSource.searchCandidate(
      query = query,
      pageNo = pageNo,
      resultsPerPage = resultPerPage
    )
  }

}