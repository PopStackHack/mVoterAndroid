package com.popstack.mvoter2015.feature.howtovote

import android.view.LayoutInflater
import android.view.ViewGroup
import com.popstack.mvoter2015.core.mvp.MvpController
import com.popstack.mvoter2015.databinding.ControllerCandidateDetailBinding
import com.popstack.mvoter2015.databinding.ControllerHowToVoteBinding

class HowToVoteController : MvpController<ControllerHowToVoteBinding, HowToVoteView, HowToVoteViewModel>(),
    HowToVoteView {

  override val viewModel: HowToVoteViewModel by contractedViewModels()

  override val bindingInflater: (LayoutInflater) -> ControllerHowToVoteBinding =
    ControllerHowToVoteBinding::inflate

}