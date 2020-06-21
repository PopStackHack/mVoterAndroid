package com.popstack.mvoter2015.data.cache.source

import com.popstack.mvoter2015.data.cache.MVoterDb
import com.popstack.mvoter2015.data.cache.entity.FaqTable
import com.popstack.mvoter2015.data.common.faq.FaqCacheSource
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
          id = faq.faqId,
          question = faq.question,
          answer = faq.answer,
          lawSource = faq.lawSource,
          articleSource = faq.articleSource,
          category = faq.category,
          shareableUrl = faq.shareableUrl
        )
      }
    }
  }

  override fun getFaqList(page: Int, itemsPerPage: Int, category: FaqCategory): List<Faq> {
    val limit = itemsPerPage
    val offset = (page - 1) * limit
    return db.faqTableQueries.selectAll(
      limit = limit.toLong(),
      offset = offset.toLong()
    ).executeAsList().map {
      it.mapToEntity(db)
    }
  }

}

fun FaqTable.mapToEntity(db: MVoterDb): Faq {
  return Faq(
    faqId = id,
    question = question,
    answer = answer,
    lawSource = lawSource,
    articleSource = articleSource,
    category = category,
    shareableUrl = shareableUrl
  )
}