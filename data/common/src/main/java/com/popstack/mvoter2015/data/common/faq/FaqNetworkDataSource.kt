package com.popstack.mvoter2015.data.common.faq

import com.popstack.mvoter2015.domain.faq.model.FaqCategoryId

interface FaqNetworkDataSource {

  fun getFaqList(page: Int, itemsPerPage: Int, categoryId: FaqCategoryId)

}