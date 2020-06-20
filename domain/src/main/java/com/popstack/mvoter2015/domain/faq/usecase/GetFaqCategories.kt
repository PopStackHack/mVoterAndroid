package com.popstack.mvoter2015.domain.faq.usecase

import com.popstack.mvoter2015.domain.CoroutineUseCase
import com.popstack.mvoter2015.domain.DispatcherProvider
import com.popstack.mvoter2015.domain.faq.model.FaqCategory
import javax.inject.Inject

class GetFaqCategories @Inject constructor(dispatcherProvider: DispatcherProvider) :
  CoroutineUseCase<Unit, List<FaqCategory>>(dispatcherProvider) {

  override fun provide(input: Unit): List<FaqCategory> {
    TODO("Not yet implemented")
  }

}