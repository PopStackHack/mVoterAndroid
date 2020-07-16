package com.popstack.mvoter2015.data.common.news

import com.popstack.mvoter2015.domain.news.model.News

interface NewsNetworkSource {

  fun getNewsList(page: Int, itemPerPage: Int): List<News>
}