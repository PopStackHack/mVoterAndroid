package com.popstack.mvoter2015.data.android.news

import android.content.Context
import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.popstack.mvoter2015.data.common.news.NewsCacheSource
import com.popstack.mvoter2015.data.common.news.NewsNetworkSource
import com.popstack.mvoter2015.domain.news.model.News
import javax.inject.Inject

class NewsPagerFactory @Inject constructor(
  private val context: Context,
  private val newsCacheSource: NewsCacheSource,
  private val newsNetworkSource: NewsNetworkSource
) {

  fun pager(itemPerPage: Int): Pager<Int, News> {
    return Pager(
      config = PagingConfig(
        pageSize = itemPerPage
      ),
      remoteMediator = NewsRemoteMediator(
        context = context,
        newsCacheSource = newsCacheSource,
        newsNetworkSource = newsNetworkSource
      ),
      pagingSourceFactory = {
        newsCacheSource.getAllPaging(itemPerPage)
      }
    )
  }

}