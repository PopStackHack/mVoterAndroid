package com.popstack.mvoter2015.feature.voteresult

import android.view.LayoutInflater
import com.popstack.mvoter2015.core.mvp.MvvmController
import com.popstack.mvoter2015.databinding.ControllerVoteResultBinding

class VoteResultController : MvvmController<ControllerVoteResultBinding>() {

  private val viewModel: VoteResultViewModel by viewModels()

  override val bindingInflater: (LayoutInflater) -> ControllerVoteResultBinding =
    ControllerVoteResultBinding::inflate

}