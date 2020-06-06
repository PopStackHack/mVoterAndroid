package com.popstack.mvoter2015.data.cache.columnadapter

import com.squareup.sqldelight.ColumnAdapter
import java.util.StringJoiner

object StringListColumnAdapter : ColumnAdapter<List<String>, String> {

  private val delimiter = "|"

  override fun decode(databaseValue: String): List<String> {
    return databaseValue.split(delimiter)
  }

  override fun encode(value: List<String>): String {
    val stringJoiner = StringJoiner(delimiter)
    value.forEach {
      stringJoiner.add(it)
    }
    return stringJoiner.toString()
  }
}