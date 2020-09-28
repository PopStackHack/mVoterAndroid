package com.popstack.mvoter2015.domain.news.usecase

import com.popstack.mvoter2015.domain.CoroutineUseCase
import com.popstack.mvoter2015.domain.DispatcherProvider
import com.popstack.mvoter2015.domain.news.model.News
import javax.inject.Inject

class GetNewsList @Inject constructor(
  dispatcherProvider: DispatcherProvider
) :
  CoroutineUseCase<GetNewsList.Params, List<News>>(
    dispatcherProvider
  ) {

  data class Params(
    val page: Int,
    val itemPerPage: Int
  )

  override suspend fun provide(input: Params): List<News> {
    return emptyList()
//    return partyRepository.getPartyList(input.page, input.itemPerPage)
  }
}