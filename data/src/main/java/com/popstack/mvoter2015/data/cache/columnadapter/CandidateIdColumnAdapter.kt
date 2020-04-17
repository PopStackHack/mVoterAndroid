package com.popstack.mvoter2015.data.cache.columnadapter

import com.popstack.mvoter2015.domain.candidate.model.CandidateId
import com.squareup.sqldelight.ColumnAdapter

object CandidateIdColumnAdapter : ColumnAdapter<CandidateId, String> {

  override fun decode(databaseValue: String): CandidateId {
    return CandidateId(
      databaseValue
    )
  }

  override fun encode(value: CandidateId): String {
    return value.value
  }
}