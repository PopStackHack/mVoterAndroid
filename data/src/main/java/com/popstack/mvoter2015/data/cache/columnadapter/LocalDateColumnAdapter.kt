package com.popstack.mvoter2015.data.cache.columnadapter

import com.squareup.sqldelight.ColumnAdapter
import java.time.LocalDate
import java.time.format.DateTimeFormatter

object LocalDateColumnAdapter : ColumnAdapter<LocalDate, String> {

  override fun decode(databaseValue: String): LocalDate {
    return LocalDate.parse(databaseValue, DateTimeFormatter.ISO_LOCAL_DATE)
  }

  override fun encode(value: LocalDate): String {
    return value.format(DateTimeFormatter.ISO_LOCAL_DATE)
  }

}