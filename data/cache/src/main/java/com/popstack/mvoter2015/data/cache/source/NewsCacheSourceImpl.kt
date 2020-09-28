package com.popstack.mvoter2015.data.cache.source

import com.popstack.mvoter2015.data.cache.MVoterDb
import com.popstack.mvoter2015.data.cache.entity.NewsTable
import com.popstack.mvoter2015.data.cache.map.mapToNews
import com.popstack.mvoter2015.data.common.news.NewsCacheSource
import com.popstack.mvoter2015.domain.news.model.News
import javax.inject.Inject

class NewsCacheSourceImpl @Inject constructor(
  private val db: MVoterDb
) : NewsCacheSource {

  override fun putNews(news: News) {
    db.newsTableQueries.insertOrReplace(
      news.id,
      news.title,
      news.summary,
      news.body,
      news.imageUrl,
      news.publishedDate,
      news.url
    )
  }

  override fun putNews(newsList: List<News>) {
    db.transaction {
      newsList.forEach(this@NewsCacheSourceImpl::putNews)
    }
  }

  override fun getNewsList(page: Int, itemPerPage: Int): List<News> {
    val limit = itemPerPage
    val offset = (page - 1) * limit
    return db.newsTableQueries.getWithPage(limit.toLong(), offset.toLong())
      .executeAsList().map(NewsTable::mapToNews)
  }

  override fun flush() {
    return db.newsTableQueries.deleteAll()
  }

}