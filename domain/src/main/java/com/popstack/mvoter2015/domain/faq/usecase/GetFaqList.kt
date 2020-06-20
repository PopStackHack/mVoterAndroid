package com.popstack.mvoter2015.domain.faq.usecase

import com.popstack.mvoter2015.domain.CoroutineUseCase
import com.popstack.mvoter2015.domain.DispatcherProvider
import com.popstack.mvoter2015.domain.faq.model.Faq
import com.popstack.mvoter2015.domain.faq.model.FaqCategoryId
import javax.inject.Inject

class GetFaqList @Inject constructor(
  dispatcherProvider: DispatcherProvider
) :
  CoroutineUseCase<GetFaqList.Params, List<Faq>>(
    dispatcherProvider
  ) {

  data class Params(
    val page: Int,
    val itemPerPage: Int,
    val categoryId: FaqCategoryId
  )

  override fun provide(input: Params): List<Faq> {
    TODO()
  }
}