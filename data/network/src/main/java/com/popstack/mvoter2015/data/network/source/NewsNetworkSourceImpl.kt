package com.popstack.mvoter2015.data.network.source

import com.popstack.mvoter2015.data.common.news.NewsNetworkSource
import com.popstack.mvoter2015.data.network.api.MvoterApi
import com.popstack.mvoter2015.data.network.api.NewsApiModel
import com.popstack.mvoter2015.data.network.helper.executeOrThrow
import com.popstack.mvoter2015.domain.news.model.News
import javax.inject.Inject

class NewsNetworkSourceImpl @Inject constructor(
  private val api: MvoterApi
) : NewsNetworkSource {

  override fun getNewsList(page: Int, itemPerPage: Int, query: String?): List<News> {
    return api.newsList(
      page = page,
      itemPerPage = itemPerPage,
      query = query
    ).executeOrThrow().data.map(NewsApiModel::mapToNews)
  }

}