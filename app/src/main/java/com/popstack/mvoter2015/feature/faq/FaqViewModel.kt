package com.popstack.mvoter2015.feature.faq

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagingData
import androidx.paging.map
import com.popstack.mvoter2015.data.android.faq.FaqPagerFactory
import com.popstack.mvoter2015.domain.faq.model.Faq
import com.popstack.mvoter2015.domain.faq.model.FaqCategory
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class FaqViewModel @Inject constructor(
  private val faqPagerFactory: FaqPagerFactory
) : ViewModel() {

  companion object {
    private const val PAGE_SIZE = 20
  }

  private var selectedFaqCategory: FaqCategory? = null

  val faqCategoryLiveData = MutableLiveData<FaqCategory>()

  var currentResult: Flow<PagingData<FaqViewItem>>? = null

  fun selectFaqCategory(faqCategory: FaqCategory): Flow<PagingData<FaqViewItem>> {

    val lastResult = currentResult
    if (faqCategory == selectedFaqCategory && lastResult != null) {
      return lastResult
    }
    selectedFaqCategory = faqCategory
    faqCategoryLiveData.postValue(selectedFaqCategory!!)

    val newResult = faqPagerFactory.faqPager(PAGE_SIZE, category = faqCategory)
      .flow
      .map<PagingData<Faq>, PagingData<FaqViewItem>> { pagingData ->
        val viewItemPagingData = pagingData.map<Faq, FaqViewItem> { faq ->
          FaqViewItem.QuestionAndAnswer(
            faqId = faq.id,
            question = faq.question,
            answer = faq.answer,
            source = faq.articleSource
          )
        }

        if (selectedFaqCategory == FaqCategory.VOTER_LIST) {
          viewItemPagingData
            .insertHeaderItem(FaqViewItem.CheckVoterList)
            .insertHeaderItem(FaqViewItem.PollingStationProhibition)
            .insertHeaderItem(FaqViewItem.BallotExample)
        } else if (selectedFaqCategory == FaqCategory.CANDIDATE) {
          viewItemPagingData.insertHeaderItem(FaqViewItem.LawAndUnfairPractices)
        } else {
          viewItemPagingData
        }
      }
    currentResult = newResult
    return newResult
  }

  fun selectedFaqCategory(): FaqCategory? {
    return selectedFaqCategory
  }
}