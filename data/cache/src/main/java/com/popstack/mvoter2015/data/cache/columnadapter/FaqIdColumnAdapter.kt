package com.popstack.mvoter2015.data.cache.columnadapter

import com.popstack.mvoter2015.domain.faq.model.FaqId
import com.squareup.sqldelight.ColumnAdapter

object FaqIdColumnAdapter : ColumnAdapter<FaqId, String> {

  override fun decode(databaseValue: String): FaqId {
    return FaqId(databaseValue)
  }

  override fun encode(value: FaqId): String {
    return value.value
  }
}