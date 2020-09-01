package com.popstack.mvoter2015.data.common.candidate

import com.popstack.mvoter2015.domain.candidate.CandidateRepository
import com.popstack.mvoter2015.domain.candidate.model.Candidate
import com.popstack.mvoter2015.domain.candidate.model.CandidateId
import com.popstack.mvoter2015.domain.constituency.model.ConstituencyId
import javax.inject.Inject

class CandidateRepositoryImpl @Inject constructor(
  private val candidateCacheSource: CandidateCacheSource,
  private val candidateNetworkSource: CandidateNetworkSource
) : CandidateRepository {

  override fun getCandidateList(constituencyId: ConstituencyId): List<Candidate> {
    return candidateNetworkSource.getCandidateList(constituencyId)
//    try {
//      val candidateListFromNetwork = candidateNetworkSource.getCandidateList(constituencyId, houseType)
//      candidateCacheSource.putCandidateList(candidateListFromNetwork)
//    } catch (exception: Exception) {
//      //Network error, see if can recover from cache
//      val candidateListFromCache = candidateCacheSource.getCandidateList(constituencyId, houseType)
//      if (candidateListFromCache.isEmpty()) {
//        //Seems data is empty, can't recover, throw error
//        throw exception
//      }
//      return candidateListFromCache
//    }
//
//    //We use database as single source of truth
//    return candidateCacheSource.getCandidateList(constituencyId, houseType)
  }

  override fun getCandidate(candidateId: CandidateId): Candidate {
    return candidateNetworkSource.getCandidate(candidateId)
    // TODO: Cache
//    return try {
//      candidateCacheSource.getCandidate(candidateId)
//    } catch (exception: Exception) {
//      val candidateFromNetwork = candidateNetworkSource.getCandidate(candidateId)
//      candidateCacheSource.putCandidate(candidateFromNetwork)
//      candidateCacheSource.getCandidate(candidateId)
//    }
  }

  override fun searchCandidate(query: String, pageNo: Int, resultPerPage: Int): List<Candidate> {
    return candidateNetworkSource.searchCandidate(
      query = query,
      pageNo = pageNo,
      resultsPerPage = resultPerPage
    )
  }

}