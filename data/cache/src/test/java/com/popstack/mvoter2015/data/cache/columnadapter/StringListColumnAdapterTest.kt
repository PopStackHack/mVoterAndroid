package com.popstack.mvoter2015.data.cache.columnadapter

import org.junit.Assert
import org.junit.Test

class StringListColumnAdapterTest {

  @Test
  fun encodeEmptyList() {
    val input = emptyList<String>()
    val expected = ""
    val actual = StringListColumnAdapter.encode(input)

    Assert.assertEquals(expected, actual)
  }

  @Test
  fun decodeEmptyString() {
    val input = ""
    val expected = emptyList<String>()
    val actual = StringListColumnAdapter.decode(input).toList()

    Assert.assertEquals(expected, actual)
  }
}