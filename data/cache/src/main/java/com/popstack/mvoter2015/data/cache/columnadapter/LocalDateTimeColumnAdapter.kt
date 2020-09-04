package com.popstack.mvoter2015.data.cache.columnadapter

import com.squareup.sqldelight.ColumnAdapter
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneOffset

object LocalDateTimeColumnAdapter : ColumnAdapter<LocalDateTime, Long> {

  override fun decode(databaseValue: Long): LocalDateTime {
    return Instant.ofEpochMilli(databaseValue).atOffset(ZoneOffset.UTC).toLocalDateTime()
  }

  override fun encode(value: LocalDateTime): Long {
    return value.toInstant(ZoneOffset.UTC).toEpochMilli()
  }
}