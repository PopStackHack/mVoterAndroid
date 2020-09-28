package com.popstack.mvoter2015.domain.candidate.usecase

import com.popstack.mvoter2015.domain.CoroutineUseCase
import com.popstack.mvoter2015.domain.DispatcherProvider
import com.popstack.mvoter2015.domain.candidate.CandidateRepository
import com.popstack.mvoter2015.domain.candidate.model.Candidate
import com.popstack.mvoter2015.domain.constituency.model.ConstituencyId
import javax.inject.Inject

class GetRivalCandidateList @Inject constructor(
  dispatcherProvider: DispatcherProvider,
  private val candidateRepository: CandidateRepository
) :
  CoroutineUseCase<GetRivalCandidateList.Params, List<Candidate>>(
    dispatcherProvider
  ) {

  data class Params(
    val constituencyId: ConstituencyId
  )

  override suspend fun provide(input: Params): List<Candidate> = input.run {
    candidateRepository.getRivalCandidatesInConstituency(constituencyId)
  }
}