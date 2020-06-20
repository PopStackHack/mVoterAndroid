package com.popstack.mvoter2015.domain.faq.usecase

import com.popstack.mvoter2015.domain.CoroutineUseCase
import com.popstack.mvoter2015.domain.DispatcherProvider
import com.popstack.mvoter2015.domain.faq.model.Faq
import com.popstack.mvoter2015.domain.faq.model.FaqId
import javax.inject.Inject

class GetFaq @Inject constructor(
  dispatcherProvider: DispatcherProvider
) :
  CoroutineUseCase<GetFaq.Params, Faq>(
    dispatcherProvider
  ) {

  data class Params(
    val faqId: FaqId
  )

  override fun provide(input: Params): Faq {
    TODO()
  }
}