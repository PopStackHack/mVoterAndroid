package com.popstack.mvoter2015.data.android.news

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.popstack.mvoter2015.data.common.news.NewsCacheSource
import com.popstack.mvoter2015.data.common.news.NewsNetworkSource
import com.popstack.mvoter2015.domain.news.model.News
import javax.inject.Inject

class NewsPagerFactory @Inject constructor(
  private val newsCacheSource: NewsCacheSource,
  private val newsNetworkSource: NewsNetworkSource
) {

  fun createPager(itemPerPage: Int, query: String? = null): Pager<Int, News> {
    return Pager(
      config = PagingConfig(
        pageSize = itemPerPage
      ),
      pagingSourceFactory = {
        if (query == null) {
          NewsPagingSource(newsCacheSource, newsNetworkSource)
        } else {
          NewsSearchPagingSource(newsNetworkSource, query)
        }
      }
    )
  }

}