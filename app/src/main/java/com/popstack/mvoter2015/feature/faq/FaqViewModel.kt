package com.popstack.mvoter2015.feature.faq

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.popstack.mvoter2015.data.android.faq.FaqPagerFactory
import com.popstack.mvoter2015.domain.faq.model.Faq
import com.popstack.mvoter2015.domain.faq.model.FaqCategory
import com.popstack.mvoter2015.domain.faq.model.FaqId
import com.popstack.mvoter2015.domain.faq.usecase.GetFaq
import com.popstack.mvoter2015.helper.livedata.SingleLiveEvent
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class FaqViewModel @ViewModelInject constructor(
  private val faqPagerFactory: FaqPagerFactory,
  private val getFaq: GetFaq
) : ViewModel() {

  companion object {
    private const val PAGE_SIZE = 20
  }

  private var selectedFaqCategory: FaqCategory? = null

  val faqCategoryLiveData = MutableLiveData<FaqCategory>()

  sealed class SingleEvent {
    data class ShareFaq(val shareUrl: String) : SingleEvent()
  }

  val viewEventLiveData =
    SingleLiveEvent<SingleEvent>()

  var currentResult: Flow<PagingData<FaqViewItem>>? = null

  fun selectFaqCategory(faqCategory: FaqCategory): Flow<PagingData<FaqViewItem>> {
    val lastResult = currentResult
    if (faqCategory == selectedFaqCategory && lastResult != null) {
      return lastResult
    }
    selectedFaqCategory = faqCategory
    faqCategoryLiveData.postValue(selectedFaqCategory)

    val newResult = faqPagerFactory.faqPager(PAGE_SIZE, category = faqCategory)
      .flow
      .map<PagingData<Faq>, PagingData<FaqViewItem>> { pagingData ->
        val viewItemPagingData = pagingData.map<Faq, FaqViewItem> { faq ->
          FaqViewItem.QuestionAndAnswer(
            faqId = faq.id,
            question = faq.question,
            answer = faq.answer
          )
        }

        if (selectedFaqCategory == FaqCategory.GENERAL) {
          viewItemPagingData
            .insertHeaderItem(FaqViewItem.PollingStationProhibition)
            .insertHeaderItem(FaqViewItem.BallotExample)
        } else {
          viewItemPagingData
        }
      }
      .cachedIn(viewModelScope)
    currentResult = newResult
    return newResult
  }

  fun selectedFaqCategory(): FaqCategory? {
    return selectedFaqCategory
  }

  fun handleShareClick(faqId: FaqId) {
    viewModelScope.launch {
      val faq = getFaq.execute(GetFaq.Params(faqId))
      viewEventLiveData.postValue(SingleEvent.ShareFaq(shareUrl = faq.shareableUrl))
    }
  }
}