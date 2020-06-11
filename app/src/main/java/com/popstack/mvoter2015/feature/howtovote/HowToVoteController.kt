package com.popstack.mvoter2015.feature.howtovote

import android.view.LayoutInflater
import com.popstack.mvoter2015.core.mvp.MvvmController
import com.popstack.mvoter2015.databinding.ControllerHowToVoteBinding

class HowToVoteController : MvvmController<ControllerHowToVoteBinding>() {

  private val viewModel: HowToVoteViewModel by viewModels()

  override val bindingInflater: (LayoutInflater) -> ControllerHowToVoteBinding =
    ControllerHowToVoteBinding::inflate

}