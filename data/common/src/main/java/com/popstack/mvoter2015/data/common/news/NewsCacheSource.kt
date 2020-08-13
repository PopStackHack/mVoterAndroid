package com.popstack.mvoter2015.data.common.news

import androidx.paging.PagingSource
import com.popstack.mvoter2015.domain.news.model.News

interface NewsCacheSource {

  fun putNews(news: News)

  fun putNews(newsList: List<News>)

  fun getNewsList(page: Int, itemPerPage: Int): List<News>

  fun getAllPaging(itemPerPage: Int): PagingSource<Int, News>
}