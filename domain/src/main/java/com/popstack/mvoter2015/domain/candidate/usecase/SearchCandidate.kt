package com.popstack.mvoter2015.domain.candidate.usecase

import com.popstack.mvoter2015.domain.CoroutineUseCase
import com.popstack.mvoter2015.domain.DispatcherProvider
import com.popstack.mvoter2015.domain.candidate.CandidateRepository
import com.popstack.mvoter2015.domain.candidate.model.Candidate
import javax.inject.Inject

class SearchCandidate @Inject constructor(
  dispatcherProvider: DispatcherProvider,
  private val candidateRepository: CandidateRepository
) :
  CoroutineUseCase<SearchCandidate.Params, List<Candidate>>(
    dispatcherProvider
  ) {

  data class Params(
    val query: String,
    val pageNo: Int,
    val resultPerPage: Int = 20
  )

  override suspend fun provide(input: Params) = input.run {
    candidateRepository.searchCandidate(
      query = query,
      pageNo = pageNo,
      resultPerPage = resultPerPage
    )
  }
}