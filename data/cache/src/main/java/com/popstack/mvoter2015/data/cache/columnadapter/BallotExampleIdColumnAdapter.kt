package com.popstack.mvoter2015.data.cache.columnadapter

import com.popstack.mvoter2015.domain.faq.model.BallotExampleId
import com.squareup.sqldelight.ColumnAdapter

object BallotExampleIdColumnAdapter : ColumnAdapter<BallotExampleId, String> {

  override fun decode(databaseValue: String): BallotExampleId {
    return BallotExampleId(databaseValue)
  }

  override fun encode(value: BallotExampleId): String {
    return value.value
  }
}