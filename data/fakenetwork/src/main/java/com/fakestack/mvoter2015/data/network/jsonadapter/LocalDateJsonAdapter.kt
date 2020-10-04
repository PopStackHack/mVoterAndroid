package com.fakestack.mvoter2015.data.network.jsonadapter

import com.popstack.mvoter2015.domain.utils.convertToEnglishNumber
import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeFormatterBuilder
import java.util.Locale

class LocalDateJsonAdapter {

  companion object {
    private val FORMATTER = DateTimeFormatterBuilder()
      .appendOptional(DateTimeFormatter.ofPattern("dd-MM-yyyy", Locale.ENGLISH))
      .appendOptional(DateTimeFormatter.ofPattern("dd-M-yyyy", Locale.ENGLISH))
      .appendOptional(DateTimeFormatter.ofPattern("d-MM-yyyy", Locale.ENGLISH))
      .appendOptional(DateTimeFormatter.ofPattern("d-M-yyyy", Locale.ENGLISH))
      .toFormatter()
  }

  @ToJson
  fun toJson(value: LocalDate): String {
    return FORMATTER.format(value)
  }

  @FromJson
  fun fromJson(value: String): LocalDate {
    return LocalDate.parse(value.convertToEnglishNumber(), FORMATTER)
  }

}