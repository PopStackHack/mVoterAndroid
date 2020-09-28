package com.popstack.mvoter2015.domain.news

import com.popstack.mvoter2015.domain.news.model.News

interface NewsRepository {

  fun getNewsList(page: Int, itemPerPage: Int): List<News>
}