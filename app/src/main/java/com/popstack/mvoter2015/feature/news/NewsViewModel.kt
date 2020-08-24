package com.popstack.mvoter2015.feature.news

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.popstack.mvoter2015.data.android.news.NewsPagerFactory
import com.popstack.mvoter2015.domain.news.model.News
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class NewsViewModel @ViewModelInject constructor(
  private val newsPagerFactory: NewsPagerFactory
) : ViewModel() {

  fun getNewsPagingFlow(): Flow<PagingData<NewsViewItem>> {
    return newsPagerFactory.createPager(20)
      .flow.map { pagingData ->
        pagingData.map<News, NewsViewItem> { news ->
          NewsViewItem(
            id = news.id,
            title = news.title,
            summary = news.summary,
            imageUrl = news.imageUrl,
            publishedDate = news.publishedDate.toString(),
            url = news.url
          )
        }
      }.cachedIn(viewModelScope)
  }
}