package com.popstack.mvoter2015.feature.news

import com.popstack.mvoter2015.domain.utils.convertToBurmeseNumber
import java.time.LocalDate
import java.time.Month

class NewsDateTimeFormatter {

  fun format(date: LocalDate): String {
    val day = date.dayOfMonth
    val month = date.month
    val year = date.year

    val burmeseMonthString = when (date.month) {
      Month.JANUARY -> "ဇန်နဝါရီ"
      Month.FEBRUARY -> "ဖေဖော်ဝါရီ"
      Month.MARCH -> "မတ်"
      Month.APRIL -> "ဧပြီ"
      Month.MAY -> "မေ"
      Month.JUNE -> "ဇွန်"
      Month.JULY -> "ဇူလိုင်"
      Month.AUGUST -> "ဩဂုတ်"
      Month.SEPTEMBER -> "စက်တင်ဘာ"
      Month.OCTOBER -> "အောက်တိုဘာ"
      Month.NOVEMBER -> "နိုဝင်ဘာ"
      Month.DECEMBER -> "ဒီဇင်ဘာ"
    }

    return "${day.toString().convertToBurmeseNumber()} $burmeseMonthString၊ ${year.toString().convertToBurmeseNumber()}"
  }

}