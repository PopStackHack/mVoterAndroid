package com.popstack.mvoter2015.data.android.news

import androidx.paging.PagingSource
import com.popstack.mvoter2015.data.common.news.NewsCacheSource
import com.popstack.mvoter2015.data.common.news.NewsNetworkSource
import com.popstack.mvoter2015.domain.news.model.News
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber

class NewsPagingSource constructor(
  private val newsCacheSource: NewsCacheSource,
  private val newsNetworkSource: NewsNetworkSource
) : PagingSource<Int, News>() {

  companion object {
    private const val STARTING_PAGE = 1
  }

  override suspend fun load(params: LoadParams<Int>): LoadResult<Int, News> {
    try {
      // Start refresh at page 1 if undefined.
      val page = params.key ?: STARTING_PAGE
      val itemPerPage = params.pageSize

      val newsList = withContext(Dispatchers.IO) {
        try {
          val newsListFromNetwork = newsNetworkSource.getNewsList(page, itemPerPage)
          if (params is LoadParams.Refresh) {
            newsCacheSource.flush()
          }
          newsCacheSource.putNews(newsListFromNetwork)
          newsCacheSource.getNewsList(page, itemPerPage)
        } catch (exception: Exception) {
          //Network error, see if can recover from cache
          val newsListFromCache = newsCacheSource.getNewsList(page, itemPerPage)
          if (newsListFromCache.isEmpty()) {
            //Seems data is empty, can't recover, throw error
            throw exception
          }
          newsListFromCache
        }
      }

      val nextKey: Int? = if (newsList.isEmpty()) {
        null
      } else {
        page + 1
      }

      return LoadResult.Page(
        data = newsList,
        prevKey = null, // Only paging forward.
        nextKey = nextKey
      )
    } catch (e: Exception) {
      Timber.e(e)
      // Handle errors in this block and return LoadResult.Error if it is an
      // expected error (such as a network failure).
      return LoadResult.Error(e)
    }
  }

}