package com.popstack.mvoter2015.feature.news

import javax.inject.Inject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.popstack.mvoter2015.data.android.news.NewsPagerFactory
import com.popstack.mvoter2015.domain.news.model.News
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class NewsViewModel @Inject constructor(
  private val newsPagerFactory: NewsPagerFactory
) : ViewModel() {

  companion object {
    private const val ITEM_PER_PAGE = 10
  }

  private val dateTimeFormatter = NewsDateTimeFormatter()

  fun getNewsPagingFlow(): Flow<PagingData<NewsViewItem>> {
    return newsPagerFactory.createPager(ITEM_PER_PAGE)
      .flow.map { pagingData ->
        pagingData.map<News, NewsViewItem> { news ->
          NewsViewItem(
            id = news.id,
            title = news.title,
            summary = news.summary,
            imageUrl = news.imageUrl,
            publishedDate = dateTimeFormatter.format(news.publishedDate),
            url = news.url
          )
        }
      }.cachedIn(viewModelScope)
  }
}