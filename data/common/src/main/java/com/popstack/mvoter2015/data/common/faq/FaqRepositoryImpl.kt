package com.popstack.mvoter2015.data.common.faq

import com.popstack.mvoter2015.domain.faq.FaqRepository
import com.popstack.mvoter2015.domain.faq.model.Faq
import com.popstack.mvoter2015.domain.faq.model.FaqCategory
import javax.inject.Inject

class FaqRepositoryImpl @Inject constructor(
  private val faqNetworkSource: FaqNetworkSource,
  private val faqCacheSource: FaqCacheSource
) : FaqRepository {

  override fun getFaq(page: Int, itemPerPage: Int, category: FaqCategory): List<Faq> {
    try {
      val faqListFromNetwork = faqNetworkSource.getFaqList(page, itemPerPage, category)
      faqCacheSource.putFaqList(faqListFromNetwork)
    } catch (exception: Exception) {
      //Network error, see if we can recover from cache
      val faqListFromCache = faqCacheSource.getFaqList(page, itemPerPage, category)
      if (faqListFromCache.isEmpty()) {
        //Seems data is empty, can't recover, throw error
        throw exception
      }
      return faqListFromCache
    }

    //We use database as single source of truth
    return faqCacheSource.getFaqList(page, itemPerPage, category)
  }

}