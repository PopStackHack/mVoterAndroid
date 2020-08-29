package com.popstack.mvoter2015.feature.faq.search

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.popstack.mvoter2015.data.android.faq.FaqPagerFactory
import com.popstack.mvoter2015.domain.faq.model.Faq
import com.popstack.mvoter2015.domain.faq.model.FaqId
import com.popstack.mvoter2015.helper.livedata.SingleLiveEvent
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class FaqSearchViewModel @ViewModelInject constructor(
  private val faqPagerFactory: FaqPagerFactory
) : ViewModel() {

  companion object {
    private const val PAGE_SIZE = 20
  }

  var currentQueryValue: String? = null
    private set

  val viewEventLiveData =
    SingleLiveEvent<SingleEvent>()

  sealed class SingleEvent {
    data class ShareFaq(val shareUrl: String) : SingleEvent()
  }

  private var currentSearchResult: Flow<PagingData<FaqSearchViewItem>>? = null

  fun search(query: String): Flow<PagingData<FaqSearchViewItem>> {
    val lastResult = currentSearchResult
    if (query == currentQueryValue && lastResult != null) {
      return lastResult
    }
    currentQueryValue = query
    val newResult: Flow<PagingData<FaqSearchViewItem>> = faqPagerFactory.faqPager(PAGE_SIZE, query = query, category = null)
      .flow.map { pagingData ->
        pagingData.map<Faq, FaqSearchViewItem> { faq ->
          FaqSearchViewItem(
            faqId = faq.id,
            question = faq.question,
            answer = faq.answer
          )
        }
      }
      .cachedIn(viewModelScope)
    currentSearchResult = newResult
    return newResult
  }

  fun handleShareClick(faqId: FaqId) {

  }

}