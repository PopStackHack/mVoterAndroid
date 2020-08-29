package com.popstack.mvoter2015.data.android.news

import androidx.paging.PagingSource
import com.popstack.mvoter2015.data.common.news.NewsNetworkSource
import com.popstack.mvoter2015.domain.news.model.News
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber

class NewsSearchPagingSource constructor(
  private val newsNetworkSource: NewsNetworkSource,
  private val query: String
) : PagingSource<Int, News>() {

  companion object {
    private const val STARTING_PAGE = 1
  }

  override suspend fun load(params: LoadParams<Int>): LoadResult<Int, News> {
    try {
      // Start refresh at page 1 if undefined.
      val page = params.key ?: STARTING_PAGE
      val itemPerPage = params.loadSize

      val newsList = withContext(Dispatchers.IO) {
        newsNetworkSource.getNewsList(page, itemPerPage, query = query)
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