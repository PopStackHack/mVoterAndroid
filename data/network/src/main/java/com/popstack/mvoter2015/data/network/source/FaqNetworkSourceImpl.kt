package com.popstack.mvoter2015.data.network.source

import com.popstack.mvoter2015.data.common.faq.FaqNetworkSource
import com.popstack.mvoter2015.data.network.api.FaqApiModel
import com.popstack.mvoter2015.data.network.api.MvoterApi
import com.popstack.mvoter2015.data.network.helper.executeOrThrow
import com.popstack.mvoter2015.data.network.jsonadapter.FaqCategoryJsonAdapter
import com.popstack.mvoter2015.domain.faq.model.BallotExample
import com.popstack.mvoter2015.domain.faq.model.Faq
import com.popstack.mvoter2015.domain.faq.model.FaqCategory
import javax.inject.Inject

class FaqNetworkSourceImpl @Inject constructor(
  private val mvoterApi: MvoterApi
) : FaqNetworkSource {

  override fun getFaqList(page: Int, itemsPerPage: Int, category: FaqCategory?, query: String?): List<Faq> {
    return mvoterApi.faqList(
      page = page,
      category = category?.let {
        FaqCategoryJsonAdapter().toJson(it)
      },
      query = query
    ).executeOrThrow().data.map(FaqApiModel::mapToFaq)
  }

  override fun getBallotExampleList(): List<BallotExample> {
    TODO("Not yet implemented")
  }

}