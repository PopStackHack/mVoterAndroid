package com.popstack.mvoter2015.data.network.jsonadapter

import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

class LocalDateJsonAdapter {

  companion object {
    private val FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy", Locale.ENGLISH)
  }

  @ToJson
  fun toJson(value: LocalDate): String {
    return FORMATTER.format(value)
  }

  @FromJson
  fun fromJson(value: String): LocalDate {
    return LocalDate.parse(value, FORMATTER)
  }

}