package com.popstack.mvoter2015.data.cache.columnadapter

import com.popstack.mvoter2015.domain.party.model.PartyId
import com.squareup.sqldelight.ColumnAdapter

object PartyIdColumnAdapter : ColumnAdapter<PartyId, String> {

  override fun decode(databaseValue: String): PartyId {
    return PartyId(databaseValue)
  }

  override fun encode(value: PartyId): String {
    return value.value
  }
}