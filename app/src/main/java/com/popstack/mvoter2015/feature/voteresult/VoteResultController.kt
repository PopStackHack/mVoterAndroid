package com.popstack.mvoter2015.feature.voteresult

import android.view.LayoutInflater
import android.view.ViewGroup
import com.popstack.mvoter2015.core.mvp.MvpController
import com.popstack.mvoter2015.databinding.ControllerPartyDetailBinding
import com.popstack.mvoter2015.databinding.ControllerVoteResultBinding

class VoteResultController : MvpController<ControllerVoteResultBinding, VoteResultView, VoteResultViewModel>(),
  VoteResultView {

  override val viewModel: VoteResultViewModel by contractedViewModels()

  override val bindingInflater: (LayoutInflater) -> ControllerVoteResultBinding =
    ControllerVoteResultBinding::inflate

}