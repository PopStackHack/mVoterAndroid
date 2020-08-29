package com.popstack.mvoter2015.data.cache.source

import androidx.paging.PagingSource
import com.popstack.mvoter2015.data.cache.MVoterDb
import com.popstack.mvoter2015.data.cache.entity.NewsTable
import com.popstack.mvoter2015.data.cache.extension.QueryDataSourceFactory
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
    return db.newsTableQueries.getWithPage(itemPerPage.toLong(), page.toLong())
      .executeAsList().map(NewsTable::mapToNews)
  }

  override fun getAllPaging(itemPerPage: Int): PagingSource<Int, News> {
    return QueryDataSourceFactory(
      queryProvider = db.newsTableQueries::getWithPage,
      countQuery = db.newsTableQueries.countAll(),
      transacter = db.newsTableQueries
    ).map(NewsTable::mapToNews).asPagingSourceFactory().invoke()
  }

  override fun getSearchPaging(itemPerPage: Int, query: String): PagingSource<Int, News> {
    TODO("Not yet implemented")
  }
}