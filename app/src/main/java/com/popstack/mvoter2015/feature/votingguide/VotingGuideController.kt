package com.popstack.mvoter2015.feature.votingguide

import android.os.Bundle
import android.view.LayoutInflater
import androidx.core.content.ContextCompat
import androidx.core.text.bold
import androidx.core.text.buildSpannedString
import androidx.core.text.color
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.popstack.mvoter2015.R
import com.popstack.mvoter2015.core.mvp.MvvmController
import com.popstack.mvoter2015.databinding.ControllerHowToVoteBinding
import com.popstack.mvoter2015.domain.utils.convertToBurmeseNumber
import com.popstack.mvoter2015.helper.conductor.requireContext
import com.popstack.mvoter2015.logging.HasTag

class VotingGuideController : MvvmController<ControllerHowToVoteBinding>(), HasTag {

  override val tag: String = "VotingGuideController"

  private val viewModel: VotingGuideViewModel by viewModels()

  override val bindingInflater: (LayoutInflater) -> ControllerHowToVoteBinding =
    ControllerHowToVoteBinding::inflate

  override fun onBindView(savedViewState: Bundle?) {
    super.onBindView(savedViewState)
    binding.rvVotingGuide.apply {
      val titles = arrayOf(
        R.string.how_to_vote_step_1_title, R.string.how_to_vote_step_2_title,
        R.string.how_to_vote_step_3_title, R.string.how_to_vote_step_4_title,
        R.string.how_to_vote_step_5_title, R.string.how_to_vote_step_6_title
      ).map {
        context.resources.getString(it)
      }

      val steps = arrayOf(
        R.array.how_to_vote_step_1, R.array.how_to_vote_step_2,
        R.array.how_to_vote_step_3, R.array.how_to_vote_step_4,
        R.array.how_to_vote_step_5, R.array.how_to_vote_step_6
      ).map {
        context.resources.getStringArray(it)
      }

      val viewItems = viewModel.constructViewItems(titles, steps)

      layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
      adapter = VotingGuideRecyclerViewAdapter(viewItems)
    }

    val accentColor = ContextCompat.getColor(requireContext(), R.color.accent)

    viewModel.countDownLiveData.observe(
      this,
      { countdown ->
        binding.contentCountdown.isVisible = countdown !is CountDownCalculator.CountDown.ShowNone
        when (countdown) {
          is CountDownCalculator.CountDown.ShowDay -> {
            binding.tvCountDown.text = buildSpannedString {
              color(
                accentColor
              ) {
                bold {
                  append("${countdown.dayLeft}".convertToBurmeseNumber())
                }
              }
              append(" ရက်")
            }
          }
          is CountDownCalculator.CountDown.ShowHourMinSec -> {
            binding.tvCountDown.text = buildSpannedString {
              color(
                accentColor
              ) {
                append("${countdown.hour}".convertToBurmeseNumber())
              }
              append(" နာရီ")
              color(
                accentColor
              ) {
                append(" ${countdown.minute}".convertToBurmeseNumber())
              }
              append(" မိနစ်")
              color(
                accentColor
              ) {
                append(" ${countdown.seconds}".convertToBurmeseNumber())
              }
              append(" စက္ကန့်")
            }
          }
        }

      }
    )

    viewModel.startCountDown()
  }

}