package com.popstack.mvoter2015.data.android.faq

import androidx.paging.PagingSource
import com.popstack.mvoter2015.data.common.faq.FaqCacheSource
import com.popstack.mvoter2015.data.common.faq.FaqNetworkSource
import com.popstack.mvoter2015.domain.faq.model.Faq
import com.popstack.mvoter2015.domain.faq.model.FaqCategory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber

class FaqPagingSource constructor(
  private val faqCacheSource: FaqCacheSource,
  private val faqNetworkSource: FaqNetworkSource,
  private val faqCategory: FaqCategory
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
        try {
          val faqListFromNetwork = faqNetworkSource.getFaqList(page, itemPerPage, faqCategory)
          if (params is LoadParams.Refresh) {
            Timber.i("db flushed")
            faqCacheSource.flushFaqUnderCategory(faqCategory)
          }
          faqCacheSource.putFaqList(faqListFromNetwork)
          faqCacheSource.getFaqList(page, itemPerPage, faqCategory)
        } catch (exception: Exception) {
          //Network error, see if can recover from cache
          val faqListFromCache = faqCacheSource.getFaqList(page, itemPerPage, faqCategory)
          if (faqListFromCache.isEmpty()) {
            //Seems data is empty, can't recover, throw error
            throw exception
          }
          faqListFromCache
        }
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