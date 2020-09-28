package com.popstack.mvoter2015.feature.votingguide

import com.popstack.mvoter2015.feature.votingguide.CountDownCalculator.CountDown
import org.junit.Assert
import org.junit.Test
import java.time.LocalDateTime
import java.time.Month

class CountDownCalculatorTest {

  private val calculator = CountDownCalculator()

  @Test
  fun test6DaysLeft() {
    val fromDateTime = LocalDateTime.of(2020, Month.NOVEMBER, 2, 10, 0)
    val toDateTime = LocalDateTime.of(2020, Month.NOVEMBER, 8, 6, 0)

    val expected = CountDown.ShowDay(6)
    val actual = calculator.calculate(fromDateTime, toDateTime)

    Assert.assertEquals(expected, actual)
  }

  @Test
  fun test5DaysLeft() {
    val fromDateTime = LocalDateTime.of(2020, Month.NOVEMBER, 3, 2, 0)
    val toDateTime = LocalDateTime.of(2020, Month.NOVEMBER, 8, 6, 0)

    val expected = CountDown.ShowDay(5)
    val actual = calculator.calculate(fromDateTime, toDateTime)

    Assert.assertEquals(expected, actual)
  }

  @Test
  fun test2DaysLeft() {
    val fromDateTime = LocalDateTime.of(2020, Month.NOVEMBER, 6, 2, 0)
    val toDateTime = LocalDateTime.of(2020, Month.NOVEMBER, 8, 6, 0)

    val expected = CountDown.ShowDay(2)
    val actual = calculator.calculate(fromDateTime, toDateTime)

    Assert.assertEquals(expected, actual)
  }

  @Test
  fun test2DaysLeftSecondCase() {
    val fromDateTime = LocalDateTime.of(2020, Month.NOVEMBER, 6, 6, 0)
    val toDateTime = LocalDateTime.of(2020, Month.NOVEMBER, 8, 6, 0)

    val expected = CountDown.ShowDay(2)
    val actual = calculator.calculate(fromDateTime, toDateTime)

    Assert.assertEquals(expected, actual)
  }

  @Test
  fun test2DaysLeftThirdCase() {
    val fromDateTime = LocalDateTime.of(2020, Month.NOVEMBER, 6, 10, 0)
    val toDateTime = LocalDateTime.of(2020, Month.NOVEMBER, 8, 6, 0)

    val expected = CountDown.ShowDay(2)
    val actual = calculator.calculate(fromDateTime, toDateTime)

    Assert.assertEquals(expected, actual)
  }

  @Test
  fun test23Hours59Minutes59Seconds() {
    val fromDateTime = LocalDateTime.of(2020, Month.NOVEMBER, 7, 6, 0, 1)
    val toDateTime = LocalDateTime.of(2020, Month.NOVEMBER, 8, 6, 0, 0)

    val expected = CountDown.ShowHourMinSec(23, 59, 59)
    val actual = calculator.calculate(fromDateTime, toDateTime)

    Assert.assertEquals(expected, actual)
  }

  @Test
  fun test1Hours0Minutes0Seconds() {
    val fromDateTime = LocalDateTime.of(2020, Month.NOVEMBER, 8, 5, 0, 0)
    val toDateTime = LocalDateTime.of(2020, Month.NOVEMBER, 8, 6, 0, 0)

    val expected = CountDown.ShowHourMinSec(1, 0, 0)
    val actual = calculator.calculate(fromDateTime, toDateTime)

    Assert.assertEquals(expected, actual)
  }

  @Test
  fun test0Hours30Minutes0Seconds() {
    val fromDateTime = LocalDateTime.of(2020, Month.NOVEMBER, 8, 5, 30, 0)
    val toDateTime = LocalDateTime.of(2020, Month.NOVEMBER, 8, 6, 0, 0)

    val expected = CountDown.ShowHourMinSec(0, 30, 0)
    val actual = calculator.calculate(fromDateTime, toDateTime)

    Assert.assertEquals(expected, actual)
  }

  @Test
  fun test0Hours0Minutes1Seconds() {
    val fromDateTime = LocalDateTime.of(2020, Month.NOVEMBER, 8, 5, 59, 59)
    val toDateTime = LocalDateTime.of(2020, Month.NOVEMBER, 8, 6, 0, 0)

    val expected = CountDown.ShowHourMinSec(0, 0, 1)
    val actual = calculator.calculate(fromDateTime, toDateTime)

    Assert.assertEquals(expected, actual)
  }

  @Test
  fun test0Hours0Minutes0Seconds() {
    val fromDateTime = LocalDateTime.of(2020, Month.NOVEMBER, 8, 6, 0, 0)
    val toDateTime = LocalDateTime.of(2020, Month.NOVEMBER, 8, 6, 0, 0)

    val expected = CountDown.ShowHourMinSec(0, 0, 0)
    val actual = calculator.calculate(fromDateTime, toDateTime)

    Assert.assertEquals(expected, actual)
  }

  @Test
  fun testDatePassedBy1Second() {
    val fromDateTime = LocalDateTime.of(2020, Month.NOVEMBER, 8, 6, 0, 1)
    val toDateTime = LocalDateTime.of(2020, Month.NOVEMBER, 8, 6, 0, 0)

    val expected = CountDown.ShowNone
    val actual = calculator.calculate(fromDateTime, toDateTime)

    Assert.assertEquals(expected, actual)
  }

  @Test
  fun testDatePassedbyNanoSec() {
    val fromDateTime = LocalDateTime.of(2020, Month.NOVEMBER, 8, 6, 0, 0, 1)
    val toDateTime = LocalDateTime.of(2020, Month.NOVEMBER, 8, 6, 0, 0)

    val expected = CountDown.ShowNone
    val actual = calculator.calculate(fromDateTime, toDateTime)

    Assert.assertEquals(expected, actual)
  }

  @Test
  fun testDatePassedSecond() {
    val fromDateTime = LocalDateTime.of(2020, Month.DECEMBER, 20, 6, 0, 0)
    val toDateTime = LocalDateTime.of(2020, Month.NOVEMBER, 8, 6, 0, 0)

    val expected = CountDown.ShowNone
    val actual = calculator.calculate(fromDateTime, toDateTime)

    Assert.assertEquals(expected, actual)
  }

}