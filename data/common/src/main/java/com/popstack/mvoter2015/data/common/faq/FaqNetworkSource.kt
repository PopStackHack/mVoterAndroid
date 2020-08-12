package com.popstack.mvoter2015.data.common.faq

import com.popstack.mvoter2015.domain.faq.model.BallotExample
import com.popstack.mvoter2015.domain.faq.model.Faq
import com.popstack.mvoter2015.domain.faq.model.FaqCategory

interface FaqNetworkSource {

  fun getFaqList(page: Int, itemsPerPage: Int, category: FaqCategory? = null, query: String? = null): List<Faq>

  fun getBallotExampleList(): List<BallotExample>

}