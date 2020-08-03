package com.popstack.mvoter2015.data.network.source

import com.popstack.mvoter2015.data.common.faq.FaqNetworkSource
import com.popstack.mvoter2015.data.network.api.MvoterApi
import com.popstack.mvoter2015.domain.faq.model.BallotExample
import com.popstack.mvoter2015.domain.faq.model.Faq
import com.popstack.mvoter2015.domain.faq.model.FaqCategory
import com.popstack.mvoter2015.domain.faq.model.FaqId
import javax.inject.Inject

class FaqNetworkSourceImpl @Inject constructor(
  private val mvoterApi: MvoterApi
) : FaqNetworkSource {

  override fun getFaqList(page: Int, itemsPerPage: Int, category: FaqCategory): List<Faq> {
    Thread.sleep(500)
    return (1..10).map {
      Faq(
        faqId = FaqId(it.toString()),
        question = "Question ($it)",
        answer = "အဖြေကတော့ မရှိသေးပါဘူးခင်ဗျာ။ သိချင်ရင်တော့ ဆုတောင်းပါဗျာ။ ဆုတောင်းရင် အကုန်ဖြစ်တယ်။ မဖြစ်တာဆိုလို့ မင်းပဲရှိတယ်။ စဉ်းစားပေ့ါကွာ",
        lawSource = "",
        articleSource = "",
        category = FaqCategory.GENERAL,
        shareableUrl = ""
      )
    }
//    throw NetworkException(errorBody = "", errorCode = 404)
  }

  override fun getBallotExampleList(): List<BallotExample> {
    TODO("Not yet implemented")
  }

}