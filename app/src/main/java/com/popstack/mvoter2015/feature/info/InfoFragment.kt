package com.popstack.mvoter2015.feature.info

import android.view.LayoutInflater
import androidx.viewbinding.ViewBinding
import com.popstack.mvoter2015.core.mvp.MvpFragment
import com.popstack.mvoter2015.databinding.FragmentInfoBinding

class InfoFragment : MvpFragment<FragmentInfoBinding, InfoView, InfoViewModel>(), InfoView {

  override val viewModel: InfoViewModel by contractedViewModels()

  override val bindingInflater: (LayoutInflater) -> ViewBinding =
    FragmentInfoBinding::inflate

}