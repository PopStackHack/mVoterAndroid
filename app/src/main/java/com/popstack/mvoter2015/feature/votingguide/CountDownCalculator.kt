package com.popstack.mvoter2015.feature.votingguide

import com.popstack.mvoter2015.feature.votingguide.CountDownCalculator.CountDown.ShowDay
import com.popstack.mvoter2015.feature.votingguide.CountDownCalculator.CountDown.ShowHourMinSec
import com.popstack.mvoter2015.feature.votingguide.CountDownCalculator.CountDown.ShowNone
import java.time.Duration
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.temporal.ChronoUnit
import javax.inject.Inject

/**
 * Countdown View logic calculcation
 */
class CountDownCalculator @Inject constructor() {

  sealed class CountDown {

    object ShowNone : CountDown()

    data class ShowDay(val dayLeft: Int) : CountDown()

    data class ShowHourMinSec(
      val hour: Int,
      val minute: Int,
      val seconds: Int
    ) : CountDown()

  }

  fun calculate(
    fromDateTime: LocalDateTime,
    toDateTime: LocalDateTime
  ): CountDown {

    val formDateTimeAtUtc = fromDateTime.atOffset(ZoneOffset.UTC)
      .toZonedDateTime()
    val toDateTimeAtUtc = toDateTime.atOffset(ZoneOffset.UTC)
      .toZonedDateTime()

    //Strip time data because we want to use human-spoken time
    //For example, difference between Oct 15 2 PM and Oct 10 6 PM should yield 5 days
    val dayDifference = Duration.between(
      formDateTimeAtUtc.toLocalDate().atStartOfDay(),
      toDateTimeAtUtc.toLocalDate().atStartOfDay()
    )

    if (dayDifference.isNegative) {
      return ShowNone
    }

    val preciseHourUntil = fromDateTime.until(toDateTimeAtUtc, ChronoUnit.HOURS)

    //Check if more than 24 hours remaining
    if (preciseHourUntil >= 24) {
      return ShowDay(
        dayDifference.toDays()
          .toInt()
      )
    } else {

      val nanoSecondsUntil = formDateTimeAtUtc.until(toDateTimeAtUtc, ChronoUnit.NANOS)

      //Pass the election day
      if (nanoSecondsUntil < 0) {
        return ShowNone
      } else {
        val durationUntil = Duration.between(
          formDateTimeAtUtc,
          toDateTimeAtUtc
        )

        val hourUntil = durationUntil.toHours()
        val minuteUntil = durationUntil.minusHours(durationUntil.toHours())
          .toMinutes()
        val secondUntil = durationUntil.minusMinutes(durationUntil.toMinutes())
          .seconds

        return ShowHourMinSec(
          hour = hourUntil.toInt(),
          minute = minuteUntil.toInt(),
          seconds = secondUntil.toInt()
        )
      }
    }
  }

}