package com.popstack.mvoter2015.feature.news

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import kotlinx.coroutines.flow.map

class NewsViewModel @ViewModelInject constructor(
  private val newsPagingSource: NewsPagingSource
) : ViewModel() {

  val newsPagingFlow = Pager(
    PagingConfig(pageSize = 10)
  ) {
    newsPagingSource
  }.flow.map { pagingData ->
    pagingData.map { news ->
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