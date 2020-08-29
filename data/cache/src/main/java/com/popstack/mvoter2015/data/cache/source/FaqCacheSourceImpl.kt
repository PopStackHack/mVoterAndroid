package com.popstack.mvoter2015.data.cache.source

import androidx.paging.PagingSource
import com.popstack.mvoter2015.data.cache.MVoterDb
import com.popstack.mvoter2015.data.cache.entity.FaqTable
import com.popstack.mvoter2015.data.cache.extension.QueryDataSourceFactory
import com.popstack.mvoter2015.data.cache.map.mapToFaq
import com.popstack.mvoter2015.data.common.faq.FaqCacheSource
import com.popstack.mvoter2015.domain.faq.model.BallotExample
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
          reason = ballotExample.reason
        )
      }
    }
  }

  override fun searchPaging(itemPerPage: Int, query: String): PagingSource<Int, Faq> {
    return QueryDataSourceFactory(
      queryProvider = { limit, offset ->
        db.faqTableQueries.selectAllWithQuery(query, limit, offset)
      },
      countQuery = db.faqTableQueries.countAllWithQuery(query),
      transacter = db.faqTableQueries
    ).map(FaqTable::mapToFaq).asPagingSourceFactory().invoke()
  }

  override fun getAllPaging(itemPerPage: Int, category: FaqCategory?): PagingSource<Int, Faq> {
    return if (category == null) {
      QueryDataSourceFactory(
        queryProvider = db.faqTableQueries::selectAll,
        countQuery = db.faqTableQueries.countAll(),
        transacter = db.faqTableQueries
      )
    } else {
      QueryDataSourceFactory(
        queryProvider = { limit, offset ->
          db.faqTableQueries.selectAllWithCategory(category, limit, offset)
        },
        countQuery = db.faqTableQueries.countAllWithCategory(category),
        transacter = db.faqTableQueries
      )
    }.map(FaqTable::mapToFaq).asPagingSourceFactory().invoke()
  }

  override fun getBallotExampleList(): List<BallotExample> {
    return db.ballotExampleTableQueries.selectAll().executeAsList().map { table ->
      BallotExample(
        id = table.id,
        image = table.image,
        isValid = table.isValid,
        reason = table.reason
      )
    }
  }

}