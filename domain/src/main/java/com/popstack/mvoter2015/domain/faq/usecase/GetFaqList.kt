package com.popstack.mvoter2015.domain.faq.usecase

import com.popstack.mvoter2015.domain.CoroutineUseCase
import com.popstack.mvoter2015.domain.DispatcherProvider
import com.popstack.mvoter2015.domain.faq.FaqRepository
import com.popstack.mvoter2015.domain.faq.model.Faq
import com.popstack.mvoter2015.domain.faq.model.FaqCategory
import javax.inject.Inject

class GetFaqList @Inject constructor(
  dispatcherProvider: DispatcherProvider,
  private val faqRepository: FaqRepository
) :
  CoroutineUseCase<GetFaqList.Params, List<Faq>>(
    dispatcherProvider
  ) {

  data class Params(
    val page: Int,
    val itemPerPage: Int,
    val category: FaqCategory
  )

  override fun provide(input: Params): List<Faq> {
    return faqRepository.getFaq(
      input.page,
      input.itemPerPage,
      input.category
    )
  }
}