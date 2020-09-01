package com.popstack.mvoter2015.domain.candidate.usecase

import com.popstack.mvoter2015.domain.CoroutineUseCase
import com.popstack.mvoter2015.domain.DispatcherProvider
import com.popstack.mvoter2015.domain.candidate.CandidateRepository
import com.popstack.mvoter2015.domain.candidate.model.Candidate
import com.popstack.mvoter2015.domain.candidate.model.CandidateId
import javax.inject.Inject

class GetCandidate @Inject constructor(
  dispatcherProvider: DispatcherProvider,
  private val candidateRepository: CandidateRepository
) :
  CoroutineUseCase<GetCandidate.Params, Candidate>(
    dispatcherProvider
  ) {

  data class Params(
    val candidateId: CandidateId
  )

  override suspend fun provide(input: Params): Candidate {
    return candidateRepository.getCandidate(input.candidateId)
  }
}