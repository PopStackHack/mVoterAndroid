package com.popstack.mvoter2015.feature.faq

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.popstack.mvoter2015.domain.faq.model.FaqCategory
import com.popstack.mvoter2015.domain.faq.model.FaqId
import com.popstack.mvoter2015.domain.faq.usecase.GetFaq
import com.popstack.mvoter2015.helper.livedata.SingleLiveEvent
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class FaqViewModel @ViewModelInject constructor(
  private val faqPagingSource: FaqPagingSource,
  private val getFaq: GetFaq
) : ViewModel() {

  companion object {
    private const val PAGE_SIZE = 20
  }

  private var selectedFaqCategory: FaqCategory = FaqCategory.GENERAL

  val faqCategoryLiveData = MutableLiveData<FaqCategory>()

  val faqPagingFlow = Pager(
    PagingConfig(
      pageSize = PAGE_SIZE
    )
  ) {
    faqPagingSource
  }.flow.map { pagingData ->
    val viewItemPagingData: PagingData<FaqViewItem> = pagingData.map { faq ->
      FaqViewItem.QuestionAndAnswer(
        faqId = faq.faqId,
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
  }.cachedIn(viewModelScope)

  sealed class SingleEvent {
    data class ShareFaq(val shareUrl: String) : SingleEvent()
  }

  val viewEventLiveData =
    SingleLiveEvent<SingleEvent>()

  fun handleSelectFaqCategory(faqCategory: FaqCategory) {
    selectedFaqCategory = faqCategory
    faqCategoryLiveData.postValue(selectedFaqCategory)
    faqPagingSource.setCategory(faqCategory)
  }

  fun selectedFaqCategory(): FaqCategory {
    return selectedFaqCategory
  }

  fun handleShareClick(faqId: FaqId) {
    viewModelScope.launch {
      val faq = getFaq.execute(GetFaq.Params(faqId))
      viewEventLiveData.postValue(SingleEvent.ShareFaq(shareUrl = faq.shareableUrl))
    }
  }
}