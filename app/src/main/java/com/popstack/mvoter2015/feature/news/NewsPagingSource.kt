package com.popstack.mvoter2015.feature.news

import androidx.paging.PagingSource
import com.popstack.mvoter2015.domain.news.model.News
import com.popstack.mvoter2015.domain.news.usecase.GetNewsList
import timber.log.Timber
import javax.inject.Inject

class NewsPagingSource @Inject constructor(
  private val getNewsList: GetNewsList
) : PagingSource<Int, News>() {

  override suspend fun load(params: LoadParams<Int>): LoadResult<Int, News> {
    try {
      // Start refresh at page 1 if undefined.
      val pageNumber = params.key ?: 1
      val newsList = getNewsList.execute(
        GetNewsList.Params(
          page = pageNumber,
          itemPerPage = params.loadSize
        )
      )

      val nextKey: Int? = if (newsList.isEmpty()) {
        null
      } else {
        pageNumber + 1
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