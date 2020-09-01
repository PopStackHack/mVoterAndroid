package com.popstack.mvoter2015.feature.faq.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.popstack.mvoter2015.data.android.faq.FaqPagerFactory
import com.popstack.mvoter2015.domain.faq.model.Faq
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class FaqSearchViewModel @Inject constructor(
  private val faqPagerFactory: FaqPagerFactory
) : ViewModel() {

  companion object {
    private const val PAGE_SIZE = 20
  }

  var currentQueryValue: String? = null
    private set

  private var currentSearchResult: Flow<PagingData<FaqSearchViewItem>>? = null

  fun search(query: String?): Flow<PagingData<FaqSearchViewItem>> {
    val lastResult = currentSearchResult
    if (query == currentQueryValue && lastResult != null) {
      return lastResult
    }
    currentQueryValue = query
    val newResult: Flow<PagingData<FaqSearchViewItem>> = faqPagerFactory.faqPager(PAGE_SIZE, query = query
      ?: "", category = null)
      .flow.map { pagingData ->
        pagingData.map<Faq, FaqSearchViewItem> { faq ->
          FaqSearchViewItem(
            faqId = faq.id,
            question = faq.question,
            answer = faq.answer,
            source = faq.articleSource
          )
        }
      }
      .cachedIn(viewModelScope)
    currentSearchResult = newResult
    return newResult
  }
}