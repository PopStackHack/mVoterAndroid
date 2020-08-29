package com.popstack.mvoter2015.data.android.faq

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.popstack.mvoter2015.data.common.faq.FaqCacheSource
import com.popstack.mvoter2015.data.common.faq.FaqNetworkSource
import com.popstack.mvoter2015.domain.faq.model.Faq
import com.popstack.mvoter2015.domain.faq.model.FaqCategory
import javax.inject.Inject

class FaqPagerFactory @OptIn(ExperimentalPagingApi::class)
@Inject constructor(
  private val faqNetworkSource: FaqNetworkSource,
  private val faqCacheSource: FaqCacheSource
) {

  fun faqPager(itemPerPage: Int, category: FaqCategory?, query: String? = null): Pager<Int, Faq> {
    return Pager(
      config = PagingConfig(
        pageSize = itemPerPage
      ),
      pagingSourceFactory = {
        if (query != null) {
          FaqSearchPagingSource(
            faqNetworkSource,
            query
          )
        } else {
          FaqPagingSource(
            faqCacheSource,
            faqNetworkSource,
            category!!
          )
        }
      }
    )
  }

}