package com.popstack.mvoter2015.data.cache.columnadapter

import com.squareup.sqldelight.ColumnAdapter
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneOffset

object LocalDateColumnAdapter : ColumnAdapter<LocalDate, Long> {

  override fun decode(databaseValue: Long): LocalDate {
    return Instant.ofEpochMilli(databaseValue).atOffset(ZoneOffset.UTC).toLocalDate()
  }

  override fun encode(value: LocalDate): Long {
    return value.atStartOfDay().atOffset(ZoneOffset.UTC).toInstant().toEpochMilli()
  }
}