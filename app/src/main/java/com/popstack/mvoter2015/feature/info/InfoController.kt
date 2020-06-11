package com.popstack.mvoter2015.feature.info

import android.view.LayoutInflater
import com.popstack.mvoter2015.core.mvp.MvvmController
import com.popstack.mvoter2015.databinding.ControllerInfoBinding

class InfoController : MvvmController<ControllerInfoBinding>() {

  private val viewModel: InfoViewModel by viewModels()

  override val bindingInflater: (LayoutInflater) -> ControllerInfoBinding =
    ControllerInfoBinding::inflate

}