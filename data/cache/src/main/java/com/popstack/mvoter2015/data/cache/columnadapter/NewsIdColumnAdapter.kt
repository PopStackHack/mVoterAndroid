package com.popstack.mvoter2015.data.cache.columnadapter

import com.popstack.mvoter2015.domain.news.model.NewsId
import com.squareup.sqldelight.ColumnAdapter

object NewsIdColumnAdapter : ColumnAdapter<NewsId, String> {

  override fun decode(databaseValue: String): NewsId {
    return NewsId(databaseValue)
  }

  override fun encode(value: NewsId): String {
    return value.value
  }
}