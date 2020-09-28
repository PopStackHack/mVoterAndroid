package com.popstack.mvoter2015.data.cache.source

import com.popstack.mvoter2015.data.cache.MVoterDb
import com.popstack.mvoter2015.data.cache.entity.FaqTable
import com.popstack.mvoter2015.data.cache.map.mapToFaq
import com.popstack.mvoter2015.data.common.faq.FaqCacheSource
import com.popstack.mvoter2015.domain.faq.model.BallotExample
import com.popstack.mvoter2015.domain.faq.model.BallotExampleCategory
import com.popstack.mvoter2015.domain.faq.model.Faq
import com.popstack.mvoter2015.domain.faq.model.FaqCategory
import javax.inject.Inject

class FaqCacheSourceImpl @Inject constructor(
  private val db: MVoterDb
) : FaqCacheSource {

  override fun putFaqList(faqList: List<Faq>) {
    db.transaction {
      faqList.forEach { faq ->

        db.faqTableQueries.insertOrReplace(
          id = faq.id,
          question = faq.question,
          answer = faq.answer,
          lawSource = faq.lawSource,
          articleSource = faq.articleSource,
          category = faq.category
        )
      }
    }
  }

  override fun getFaqList(page: Int, itemsPerPage: Int, category: FaqCategory): List<Faq> {
    val limit = itemsPerPage
    val offset = (page - 1) * limit
    return db.faqTableQueries.selectAllWithCategory(
      limit = limit.toLong(),
      offset = offset.toLong(),
      category = category
    ).executeAsList().map(FaqTable::mapToFaq)
  }

  override fun putBallotExampleList(ballotExampleList: List<BallotExample>) {
    db.transaction {
      ballotExampleList.forEach { ballotExample ->
        db.ballotExampleTableQueries.insertOrReplace(
          id = ballotExample.id,
          image = ballotExample.image,
          isValid = ballotExample.isValid,
          reason = ballotExample.reason,
          category = ballotExample.category
        )
      }
    }
  }

  override fun flushBallotExampleUnderCategory(category: BallotExampleCategory) {
    db.ballotExampleTableQueries.deleteByCategory(category)
  }

  override fun flushFaqUnderCategory(category: FaqCategory) {
    db.faqTableQueries.deleteByCategory(category)
  }

  override fun getBallotExampleList(ballotExampleCategory: BallotExampleCategory): List<BallotExample> {
    return db.ballotExampleTableQueries.selectAllWithCategory(ballotExampleCategory).executeAsList().map { table ->
      BallotExample(
        id = table.id,
        image = table.image,
        isValid = table.isValid,
        reason = table.reason,
        category = table.category
      )
    }
  }

}