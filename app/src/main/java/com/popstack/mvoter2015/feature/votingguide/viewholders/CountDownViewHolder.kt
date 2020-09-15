package com.popstack.mvoter2015.feature.votingguide.viewholders

import android.graphics.Color
import android.os.CountDownTimer
import androidx.core.content.ContextCompat
import androidx.core.text.bold
import androidx.core.text.buildSpannedString
import androidx.core.text.color
import androidx.core.view.isVisible
import com.popstack.mvoter2015.R
import com.popstack.mvoter2015.databinding.ItemElectionCountdownBinding
import com.popstack.mvoter2015.domain.utils.BurmeseNumberUtils
import com.popstack.mvoter2015.feature.votingguide.VotingGuideViewItem
import timber.log.Timber
import java.util.*
import java.util.concurrent.TimeUnit

class CountDownViewHolder(private val binding: ItemElectionCountdownBinding) :
  VotingGuideViewHolder(binding.root) {

  private val startCalendar = Calendar.getInstance(TimeZone.getTimeZone("Asia/Yangon"))
  private val endCalendar = Calendar.getInstance(TimeZone.getTimeZone("Asia/Yangon")).apply {
    set(2020, 10, 8, 6, 0, 0)
  }

  private val accentColor = ContextCompat.getColor(itemView.context, R.color.accent)

  private val startTime: Long = startCalendar.timeInMillis
  private val endTime: Long = endCalendar.timeInMillis
  private val totalTime = endTime - startTime
  private var countDownTimer: CountDownTimer = object : CountDownTimer(totalTime, 1000) {
    override fun onTick(millisUntilFinished: Long) {
      var remainingTime = millisUntilFinished
      val days: Long = TimeUnit.MILLISECONDS.toDays(remainingTime)

      val countDown = if (days >= 1) {
        Timber.e(days.toString())
        buildSpannedString {
          color(
            accentColor
          ) {
            bold {
              append("${days.convertToMMInt()}")
            }
          }
          append(" ရက်")
        }
      } else {
        remainingTime -= TimeUnit.DAYS.toMillis(days)
        val hours = TimeUnit.MILLISECONDS.toHours(remainingTime)
        remainingTime -= TimeUnit.HOURS.toMillis(hours)
        val minutes = TimeUnit.MILLISECONDS.toMinutes(remainingTime)
        remainingTime -= TimeUnit.MINUTES.toMillis(minutes)
        val seconds = TimeUnit.MILLISECONDS.toSeconds(remainingTime)

        buildSpannedString {
          color(
            accentColor
          ) {
            append("${hours.convertToMMInt()}")
          }
          append(" နာရီ")
          color(
            accentColor
          ) {
            append(" ${minutes.convertToMMInt()}")
          }
          append(" မိနစ်")
          color(
            accentColor
          ) {
            append(" ${seconds.convertToMMInt()}")
          }
          append(" စက္ကန့်")
        }
      }

      binding.tvCountDown.text = countDown
    }

    override fun onFinish() {
      binding.root.isVisible = false
    }
  }

  override fun bind(viewItem: VotingGuideViewItem) {
    Timber.e(startCalendar.toString())
    countDownTimer.start()
  }

  private fun Long.convertToMMInt(): CharSequence {
    return BurmeseNumberUtils.convertEnglishToBurmeseNumber(this.toString())
  }

  fun stopCountDown() {
    countDownTimer.cancel()
  }
}