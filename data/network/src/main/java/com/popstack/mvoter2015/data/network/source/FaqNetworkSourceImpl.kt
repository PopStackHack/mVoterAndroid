package com.popstack.mvoter2015.data.network.source

import com.popstack.mvoter2015.data.common.faq.FaqNetworkSource
import com.popstack.mvoter2015.data.network.api.MvoterApi
import com.popstack.mvoter2015.data.network.exception.NetworkException
import com.popstack.mvoter2015.domain.faq.model.Faq
import com.popstack.mvoter2015.domain.faq.model.FaqCategory
import javax.inject.Inject

class FaqNetworkSourceImpl @Inject constructor(
  private val mvoterApi: MvoterApi
) : FaqNetworkSource {

  override fun getFaqList(page: Int, itemsPerPage: Int, category: FaqCategory): List<Faq> {
    throw NetworkException(errorBody = "", errorCode = 404)
  }

}