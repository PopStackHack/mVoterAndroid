package com.popstack.mvoter2015.feature.votingguide

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.popstack.mvoter2015.feature.votingguide.CountDownCalculator.CountDown
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.time.Duration
import java.time.LocalDateTime
import java.time.Month
import java.time.ZoneId
import java.time.ZonedDateTime
import javax.inject.Inject

class VotingGuideViewModel @Inject constructor(
  private val countDownCalculator: CountDownCalculator
) : ViewModel() {

  fun constructViewItems(
    sectionTitles: List<String>,
    steps: List<Array<String>>
  ): List<VotingGuideViewItem> = ArrayList<VotingGuideViewItem>().apply {
    add(Header)
    sectionTitles.forEachIndexed { index, value ->
      add(SectionTitle(value))
      steps[index].forEachIndexed { stepIndex, step ->
        add(Step(step, stepIndex != 0, stepIndex != steps[index].size - 1))
      }
    }
  }

  val countDownLiveData = MutableLiveData<CountDown>()

  var countDownJob: Job? = null

  fun startCountDown() {
    countDownJob?.cancel()
    countDownJob = viewModelScope.launch {
      val electionDate = ZonedDateTime.of(
        2020,
        Month.NOVEMBER.value,
        8,
        6,
        0,
        0,
        0,
        ZoneId.of("Asia/Rangoon")
      )

      while (true) {
        val countdown =
          countDownCalculator.calculate(LocalDateTime.now(), electionDate.toLocalDateTime())
        countDownLiveData.postValue(countdown)
        if (countdown !is CountDown.ShowHourMinSec) {
          this.cancel()
        }
        delay(Duration.ofSeconds(1).toMillis())
      }
    }
  }

  override fun onCleared() {
    countDownJob?.cancel()
    super.onCleared()
  }

}