package com.popstack.mvoter2015.data.android.faq

import androidx.paging.PagingSource
import com.popstack.mvoter2015.data.common.faq.FaqNetworkSource
import com.popstack.mvoter2015.domain.faq.model.Faq
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber

class FaqSearchPagingSource constructor(
  private val faqNetworkSource: FaqNetworkSource,
  private val query: String
) : PagingSource<Int, Faq>() {

  companion object {
    private const val STARTING_PAGE = 1
  }

  override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Faq> {
    try {
      // Start refresh at page 1 if undefined.
      val page = params.key ?: STARTING_PAGE
      val itemPerPage = params.pageSize

      val faqList = withContext(Dispatchers.IO) {
        faqNetworkSource.getFaqList(page, itemPerPage, query = query)
      }

      val nextKey: Int? = if (faqList.isEmpty()) {
        null
      } else {
        page + 1
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
      return LoadResult.Error(e)
    }
  }

}