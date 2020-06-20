package com.popstack.mvoter2015.feature.info

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.popstack.mvoter2015.domain.faq.model.FaqCategoryId
import com.popstack.mvoter2015.domain.faq.model.FaqId
import com.popstack.mvoter2015.domain.faq.usecase.GetFaq
import com.popstack.mvoter2015.helper.SingleLiveEvent
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class InfoViewModel @ViewModelInject constructor(
  private val faqPagingSource: FaqPagingSource,
  private val getFaq: GetFaq
) : ViewModel() {

  companion object {
    private const val PAGE_SIZE = 20
  }

  private var selectedFaqCategoryId: FaqCategoryId? = null

  val faqPagingFlow = Pager(
    PagingConfig(
      pageSize = PAGE_SIZE
    )
  ) {
    faqPagingSource
  }.flow.map { pagingData ->
    val viewItemPagingData: PagingData<InfoViewItem> = pagingData.map { faq ->
      InfoViewItem.FaqViewItem(
        faqId = faq.faqId,
        question = faq.question,
        answer = faq.answer
      )
    }

    if (selectedFaqCategoryId == FaqCategoryId("1")) {
      viewItemPagingData.insertHeaderItem(InfoViewItem.BallotExample)
        .insertHeaderItem(InfoViewItem.PollingStationProhibition)
    } else {
      viewItemPagingData
    }
  }.cachedIn(viewModelScope)

  sealed class SingleCommand {
    data class ShareFaq(val shareUrl: String) : SingleCommand()
  }

  val singleCommandLiveData = SingleLiveEvent<SingleCommand>()

  fun handleSelectFaqCategory(faqCategoryId: FaqCategoryId) {
    faqPagingSource.setCategoryId(faqCategoryId)
  }

  fun handleShareClick(faqId: FaqId) {
    viewModelScope.launch {
      val faq = getFaq.execute(GetFaq.Params(faqId))
      singleCommandLiveData.postValue(SingleCommand.ShareFaq(shareUrl = faq.shareableUrl))
    }
  }
}