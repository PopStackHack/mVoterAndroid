package com.popstack.mvoter2015.feature.party.detail

import android.view.LayoutInflater
import androidx.viewbinding.ViewBinding
import com.popstack.mvoter2015.core.mvp.MvpFragment
import com.popstack.mvoter2015.databinding.ControllerPartyDetailBinding

class PartyDetailFragment : MvpFragment<ControllerPartyDetailBinding, PartyDetailView, PartyDetailViewModel>() {

  override val viewModel: PartyDetailViewModel by contractedViewModels()

  override val bindingInflater: (LayoutInflater) -> ViewBinding =
    ControllerPartyDetailBinding::inflate

  override fun onBindView() {
    super.onBindView()
  }
}