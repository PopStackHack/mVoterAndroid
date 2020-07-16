package com.popstack.mvoter2015.data.common.news

import com.popstack.mvoter2015.domain.news.NewsRepository
import com.popstack.mvoter2015.domain.news.model.News
import javax.inject.Inject

class NewsRepositoryImpl @Inject constructor(
  private val newsNetworkSource: NewsNetworkSource,
  private val newsCacheSource: NewsCacheSource
) : NewsRepository {

  override fun getNewsList(page: Int, itemPerPage: Int): List<News> {

    try {
      val newsListFromNetwork = newsNetworkSource.getNewsList(page, itemPerPage)
      newsCacheSource.putNews(newsListFromNetwork)
    } catch (exception: Exception) {
      //Network error, see if can recover from cache
      val newsListFromCache = newsCacheSource.getNewsList(page, itemPerPage)
      if (newsListFromCache.isEmpty()) {
        //Seems data is empty, can't recover, throw error
        throw exception
      }
      return newsListFromCache
    }

    //We use database as single source of truth
    return newsCacheSource.getNewsList(page, itemPerPage)
  }

}