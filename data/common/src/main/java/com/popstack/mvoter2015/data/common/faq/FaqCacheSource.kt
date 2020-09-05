package com.popstack.mvoter2015.data.common.faq

import com.popstack.mvoter2015.domain.faq.model.BallotExample
import com.popstack.mvoter2015.domain.faq.model.BallotExampleCategory
import com.popstack.mvoter2015.domain.faq.model.Faq
import com.popstack.mvoter2015.domain.faq.model.FaqCategory

interface FaqCacheSource {

  fun putFaqList(faqList: List<Faq>)

  fun getFaqList(page: Int, itemsPerPage: Int, category: FaqCategory): List<Faq>

  fun flushFaqUnderCategory(category: FaqCategory)

  fun getBallotExampleList(ballotExampleCategory: BallotExampleCategory): List<BallotExample>

  fun putBallotExampleList(ballotExampleList: List<BallotExample>)

  fun flushBallotExampleUnderCategory(category: BallotExampleCategory)

}