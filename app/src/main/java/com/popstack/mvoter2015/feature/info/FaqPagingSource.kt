package com.popstack.mvoter2015.feature.info

import androidx.paging.PagingSource
import com.popstack.mvoter2015.domain.faq.model.Faq
import com.popstack.mvoter2015.domain.faq.model.FaqCategoryId
import com.popstack.mvoter2015.domain.faq.usecase.GetFaqList
import timber.log.Timber
import javax.inject.Inject

class FaqPagingSource @Inject constructor(
  private val getFaqList: GetFaqList
) : PagingSource<Int, Faq>() {

  private var categoryId: FaqCategoryId? = null

  fun setCategoryId(categoryId: FaqCategoryId) {
    this.categoryId = categoryId
    invalidate()
  }

  override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Faq> {
    try {
      // Start refresh at page 1 if undefined.
      if (categoryId == null) {
        return LoadResult.Error(CategoryNotSelectedException())
      }

      val pageNumber = params.key ?: 1
      val partyList = getFaqList.execute(
        GetFaqList.Params(
          page = pageNumber,
          itemPerPage = params.loadSize,
          categoryId = categoryId!!
        )
      )

      val nextKey: Int? = if (partyList.isEmpty()) {
        null
      } else {
        pageNumber + 1
      }

      return LoadResult.Page(
        data = partyList,
        prevKey = null, // Only paging forward.
        nextKey = nextKey
      )
    } catch (e: Exception) {
      Timber.e(e)
      // Handle errors in this block and return LoadResult.Error if it is an
      // expected error (such as a network failure).
      return LoadResult.Error(e)
    }
  }
}