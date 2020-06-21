package com.popstack.mvoter2015.domain.faq

import com.popstack.mvoter2015.domain.faq.model.Faq
import com.popstack.mvoter2015.domain.faq.model.FaqCategory

interface FaqRepository {

  fun getFaq(
    page: Int,
    itemPerPage: Int,
    category: FaqCategory
  ): List<Faq>

}