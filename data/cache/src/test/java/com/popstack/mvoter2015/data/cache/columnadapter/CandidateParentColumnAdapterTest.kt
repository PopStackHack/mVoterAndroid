package com.popstack.mvoter2015.data.cache.columnadapter

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.popstack.mvoter2015.domain.candidate.model.CandidateParent
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class CandidateParentColumnAdapterTest {

  @Test
  fun testEncode() {
    val input = CandidateParent(
      name = "Aung",
      religion = "Buddhist",
      ethnicity = "Burmese"
    )

    val expected =
      """
      {"ethnicity":"Burmese","name":"Aung","religion":"Buddhist"}
      """.trimIndent()

    val actual = CandidateParentColumnAdapter.encode(input)
    Assert.assertEquals(expected, actual)
  }

  @Test
  fun testDecode() {
    val input =
      """
      {"ethnicity":"Burmese","name":"Aung","religion":"Buddhist"}
      """.trimIndent()

    val expected = CandidateParent(
      name = "Aung",
      religion = "Buddhist",
      ethnicity = "Burmese"
    )

    val actual = CandidateParentColumnAdapter.decode(input)
    Assert.assertEquals(expected, actual)
  }
}