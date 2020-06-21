package com.popstack.mvoter2015.feature.faq

import androidx.hilt.lifecycle.ViewModelInject
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

  private var selectedFaqCategoryId: FaqCategory = FaqCategory.GENERAL

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

    if (selectedFaqCategoryId == FaqCategory.GENERAL) {
      viewItemPagingData.insertHeaderItem(FaqViewItem.BallotExample)
        .insertHeaderItem(FaqViewItem.PollingStationProhibition)
    } else {
      viewItemPagingData
    }
  }.cachedIn(viewModelScope)

  sealed class SingleCommand {
    data class ShareFaq(val shareUrl: String) : SingleCommand()
  }

  val singleCommandLiveData =
    SingleLiveEvent<SingleCommand>()

  fun handleSelectFaqCategory(faqCategory: FaqCategory) {
    selectedFaqCategoryId = faqCategory
    faqPagingSource.setCategory(faqCategory)
  }

  fun handleShareClick(faqId: FaqId) {
    viewModelScope.launch {
      val faq = getFaq.execute(GetFaq.Params(faqId))
      singleCommandLiveData.postValue(SingleCommand.ShareFaq(shareUrl = faq.shareableUrl))
    }
  }
}