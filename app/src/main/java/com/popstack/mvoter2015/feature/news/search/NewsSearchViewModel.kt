package com.popstack.mvoter2015.feature.news.search

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.popstack.mvoter2015.data.android.news.NewsPagerFactory
import com.popstack.mvoter2015.domain.news.model.News
import com.popstack.mvoter2015.feature.news.NewsDateTimeFormatter
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class NewsSearchViewModel @ViewModelInject constructor(
  private val newsPagerFactory: NewsPagerFactory
) : ViewModel() {

  companion object {
    private const val PAGE_SIZE = 20
  }

  private val dateTimeFormatter = NewsDateTimeFormatter()

  var currentQueryValue: String? = null
    private set

  private var currentSearchResult: Flow<PagingData<NewsSearchViewItem>>? = null

  fun search(query: String?): Flow<PagingData<NewsSearchViewItem>> {
    val lastResult = currentSearchResult
    if (query == currentQueryValue && lastResult != null) {
      return lastResult
    }
    currentQueryValue = query
    val newResult: Flow<PagingData<NewsSearchViewItem>> = newsPagerFactory.createPager(PAGE_SIZE, query)
      .flow
      .map<PagingData<News>, PagingData<NewsSearchViewItem>> { pagingData ->
        pagingData.map<News, NewsSearchViewItem> { news ->
          NewsSearchViewItem(
            id = news.id,
            title = news.title,
            summary = news.summary,
            imageUrl = news.imageUrl,
            publishedDate = dateTimeFormatter.format(news.publishedDate),
            url = news.url
          )
        }
      }
      .cachedIn(viewModelScope)
    currentSearchResult = newResult
    return newResult
  }

}