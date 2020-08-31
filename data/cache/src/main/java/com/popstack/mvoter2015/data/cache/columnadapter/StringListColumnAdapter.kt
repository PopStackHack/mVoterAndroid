package com.popstack.mvoter2015.data.cache.columnadapter

import com.squareup.sqldelight.ColumnAdapter
import java.util.StringJoiner

internal class StringListColumnAdapter(
  private val delimiter: String = "|"
) : ColumnAdapter<List<String>, String> {

  override fun decode(databaseValue: String): List<String> {
    if (databaseValue.isEmpty()) return emptyList()
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