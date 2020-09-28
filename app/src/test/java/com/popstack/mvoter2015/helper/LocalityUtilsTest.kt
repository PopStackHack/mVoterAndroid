package com.popstack.mvoter2015.helper

import org.junit.Assert
import org.junit.Test

class LocalityUtilsTest {
  @Test
  fun testIfNptWithCorrectTownship() {
    val townshipFromNpt = "ဇမ္ဗူသီရိမြို့နယ်"
    val expectedResult = true
    val actualResult = LocalityUtils.isTownshipFromNPT(townshipFromNpt)
    Assert.assertEquals(expectedResult, actualResult)
  }

  @Test
  fun testIfNptWithInCorrectTownship() {
    val townshipNotFromNpt = "townshipNotFromNpt"
    val expectedResult = false
    val actualResult = LocalityUtils.isTownshipFromNPT(townshipNotFromNpt)
    Assert.assertEquals(expectedResult, actualResult)
  }

  @Test
  fun testIfNptWithCorrectTownshipThatHasRedundantSpaces() {
    val townshipFromNpt = "ဇမ္ဗူသီရိ မြို့နယ်"
    val expectedResult = true
    val actualResult = LocalityUtils.isTownshipFromNPT(townshipFromNpt)
    Assert.assertEquals(expectedResult, actualResult)
  }
}