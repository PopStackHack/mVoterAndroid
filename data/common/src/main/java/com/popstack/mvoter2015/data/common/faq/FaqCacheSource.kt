package com.popstack.mvoter2015.data.common.faq

import com.popstack.mvoter2015.domain.faq.model.Faq
import com.popstack.mvoter2015.domain.faq.model.FaqCategoryId

interface FaqCacheSource {

  fun putFaqList(faqList: List<Faq>)

  fun getFaqList(page: Int, itemsPerPage: Int, categoryId: FaqCategoryId): List<Faq>

}