package com.popstack.mvoter2015.data.cache.columnadapter

import com.popstack.mvoter2015.domain.faq.model.FaqCategoryId
import com.squareup.sqldelight.ColumnAdapter

object FaqCategoryIdColumnAdapter : ColumnAdapter<FaqCategoryId, String> {

  override fun decode(databaseValue: String): FaqCategoryId {
    return FaqCategoryId(databaseValue)
  }

  override fun encode(value: FaqCategoryId): String {
    return value.value
  }
}