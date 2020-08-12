package com.popstack.mvoter2015.feature.votingguide

import android.os.Bundle
import android.view.LayoutInflater
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.popstack.mvoter2015.R
import com.popstack.mvoter2015.core.mvp.MvvmController
import com.popstack.mvoter2015.databinding.ControllerHowToVoteBinding
import com.popstack.mvoter2015.sentry.HasTag

class VotingGuideController : MvvmController<ControllerHowToVoteBinding>(), HasTag {

  override val tag: String = "VotingGuideController"

  private val viewModel: VotingGuideModel by viewModels()

  override val bindingInflater: (LayoutInflater) -> ControllerHowToVoteBinding =
    ControllerHowToVoteBinding::inflate

  override fun onBindView(savedViewState: Bundle?) {
    super.onBindView(savedViewState)
    binding.rvVotingGuide.apply {
      val titles = arrayOf(
        R.string.how_to_vote_step_1_title, R.string.how_to_vote_step_2_title,
        R.string.how_to_vote_step_3_title, R.string.how_to_vote_step_4_title
      ).map {
        context.resources.getString(it)
      }

      val steps = arrayOf(
        R.array.how_to_vote_step_1, R.array.how_to_vote_step_2,
        R.array.how_to_vote_step_3, R.array.how_to_vote_step_4
      ).map {
        context.resources.getStringArray(it)
      }

      val viewItems = viewModel.constructViewItems(titles, steps)

      layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
      adapter = VotingGuideRecyclerViewAdapter(viewItems)
    }
  }

}