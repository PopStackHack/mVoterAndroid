package com.popstack.mvoter2015.feature.faq

import androidx.paging.PagingSource
import com.popstack.mvoter2015.domain.faq.model.Faq
import com.popstack.mvoter2015.domain.faq.model.FaqCategory
import com.popstack.mvoter2015.domain.faq.usecase.GetFaqList
import com.popstack.mvoter2015.exception.GlobalExceptionHandler
import com.popstack.mvoter2015.exception.PagingExceptionWrapper
import timber.log.Timber
import javax.inject.Inject

class FaqPagingSource @Inject constructor(
  private val getFaqList: GetFaqList,
  private val globalExceptionHandler: GlobalExceptionHandler
) : PagingSource<Int, Faq>() {

  private var category: FaqCategory = FaqCategory.GENERAL

  fun setCategory(category: FaqCategory) {
    this.category = category
  }

  override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Faq> {
    try {
      // Start refresh at page 1 if undefined.
      val pageNumber = params.key ?: 1
      val faqList = getFaqList.execute(
        GetFaqList.Params(
          page = pageNumber,
          itemPerPage = params.loadSize,
          category = category
        )
      )

      val nextKey: Int? = if (faqList.isEmpty()) {
        null
      } else {
        pageNumber + 1
      }

      return LoadResult.Page(
        data = faqList,
        prevKey = null, // Only paging forward.
        nextKey = nextKey
      )
    } catch (e: Exception) {
      Timber.e(e)
      // Handle errors in this block and return LoadResult.Error if it is an
      // expected error (such as a network failure).
      return LoadResult.Error(
        PagingExceptionWrapper(
          errorMessage = globalExceptionHandler.getMessageForUser(e),
          originalException = e
        )
      )
    }
  }
}