package com.popstack.mvoter2015.data.common.faq

import com.popstack.mvoter2015.domain.faq.model.Faq
import com.popstack.mvoter2015.domain.faq.model.FaqCategory

interface FaqNetworkSource {

  fun getFaqList(page: Int, itemsPerPage: Int, category: FaqCategory): List<Faq>

}