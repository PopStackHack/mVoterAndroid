package com.popstack.mvoter2015.domain.faq.usecase

import com.popstack.mvoter2015.domain.CoroutineUseCase
import com.popstack.mvoter2015.domain.DispatcherProvider
import com.popstack.mvoter2015.domain.faq.FaqRepository
import com.popstack.mvoter2015.domain.faq.model.BallotExample
import com.popstack.mvoter2015.domain.faq.model.BallotExampleCategory
import javax.inject.Inject

class GetBallotExampleList @Inject constructor(
  dispatcherProvider: DispatcherProvider,
  private val faqRepository: FaqRepository
) :
  CoroutineUseCase<BallotExampleCategory, List<BallotExample>>(dispatcherProvider) {

  override suspend fun provide(input: BallotExampleCategory): List<BallotExample> {
    return faqRepository.getBallotExample(input)
  }

}