package com.popstack.mvoter2015.domain.utils

import org.junit.Assert
import org.junit.Test

/**
 * Fork of https://github.com/vincent-paing/myanmar-phonenumber-kt/blob/master/src/main/kotlin/com/aungkyawpaing/mmphonenumber/extract/MyanmarPhoneNumberExtractor.kt
 * Supports 01 number
 */
class MyanmarPhoneNumberExtractorWithZeroOneSupportTest {

  private val extractor = MyanmarPhoneNumberExtractorWithZeroOneSupport()

  @Test
  fun inputNoNumberReturnNull() {
    val input = "နံပါတ်မပါ"
    val expected = emptyList<String>()
    val actual = extractor.extract(input)
    Assert.assertEquals(expected, actual)
  }

  @Test
  fun inputNumberReturnNumber() {
    val input = "၀၉၁၂၃၄၅၆၇ (မောင်မောင်)"
    val expected = listOf("091234567")
    val actual = extractor.extract(input)
    Assert.assertEquals(expected, actual)
  }

  @Test
  fun inputMultipleNumberReturnNumber() {
    val input = "၀၉၁၂၃၄၅၆၇ (မောင်မောင်)၊ ၀၉၁၂၃၄၅၆၆ (အောင်အောင်)"
    val expected = listOf("091234567", "091234566")
    val actual = extractor.extract(input)
    Assert.assertEquals(expected, actual)
  }

  @Test
  fun inputDirtyNumberReturnNumber() {
    val input = "၀၉-၁၂၃၄၅၆၇ (မောင်မောင်)"
    val expected = listOf("091234567")
    val actual = extractor.extract(input)
    Assert.assertEquals(expected, actual)
  }

  @Test
  fun inputZeroOneNumberReturnNumber() {
    val input = "၀၁၅၅၄၂၁၉ (မောင်မောင်)"
    val expected = listOf("01554219")
    val actual = extractor.extract(input)
    Assert.assertEquals(expected, actual)
  }

  @Test
  fun inputZeroOneDirtyNumberReturnNumber() {
    val input = "၀၁ ၅၅၄၂၁၉ (မောင်မောင်)"
    val expected = listOf("01554219")
    val actual = extractor.extract(input)
    Assert.assertEquals(expected, actual)
  }

  @Test
  fun inputMultipleZeroOneNumberReturnNumber() {
    val input = "၀၁၅၅၄၂၁၉ (မောင်မောင်)၊ ၀၁ ၂၃၄၅၆၆ (အောင်အောင်)"
    val expected = listOf("01554219", "01234566")
    val actual = extractor.extract(input)
    Assert.assertEquals(expected, actual)
  }

  @Test
  fun inputMultipleMixedNumberReturnNumber() {
    val input = "၀၁၅၅၄၂၁၉ (မောင်မောင်)၊ ၀၉-၁၂၃၄၅၆၇ (အောင်အောင်)"
    val expected = listOf("01554219", "091234567")
    val actual = extractor.extract(input)
    Assert.assertEquals(expected, actual)
  }

  @Test
  fun testEdgeCaseOne() {
    val input = "၀၉ ၅၁၈၆၉၇၄(ဦးစိုင်းအောင်မြင့်ခိုင်)"
    val expected = listOf("095186974")
    val actual = extractor.extract(input)
    Assert.assertEquals(expected, actual)
  }

}