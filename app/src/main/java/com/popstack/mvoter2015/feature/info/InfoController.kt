package com.popstack.mvoter2015.feature.info

import android.view.LayoutInflater
import com.popstack.mvoter2015.core.mvp.MvpController
import com.popstack.mvoter2015.databinding.ControllerInfoBinding

class InfoController : MvpController<ControllerInfoBinding, InfoView, InfoViewModel>(), InfoView {

  override val viewModel: InfoViewModel by contractedViewModels()

  override val bindingInflater: (LayoutInflater) -> ControllerInfoBinding =
    ControllerInfoBinding::inflate

}