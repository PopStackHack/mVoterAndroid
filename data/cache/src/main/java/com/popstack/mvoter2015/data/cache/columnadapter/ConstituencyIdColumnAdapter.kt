package com.popstack.mvoter2015.data.cache.columnadapter

import com.popstack.mvoter2015.domain.constituency.model.ConstituencyId
import com.squareup.sqldelight.ColumnAdapter

object ConstituencyIdColumnAdapter : ColumnAdapter<ConstituencyId, String> {

  override fun decode(databaseValue: String): ConstituencyId {
    return ConstituencyId(
      databaseValue
    )
  }

  override fun encode(value: ConstituencyId): String {
    return value.value
  }
}