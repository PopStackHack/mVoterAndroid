package com.popstack.mvoter2015.feature.party.detail

import android.view.LayoutInflater
import androidx.navigation.fragment.navArgs
import androidx.viewbinding.ViewBinding
import com.popstack.mvoter2015.core.mvp.MvpFragment
import com.popstack.mvoter2015.databinding.FragmentPartyDetailBinding

class PartyDetailFragment : MvpFragment<FragmentPartyDetailBinding, PartyDetailView, PartyDetailViewModel>() {

  override val viewModel: PartyDetailViewModel by contractedViewModels()

  override val bindingInflater: (LayoutInflater) -> ViewBinding =
    FragmentPartyDetailBinding::inflate

  private val navArg: PartyDetailFragmentArgs
    by navArgs()

  override fun onBindView() {
    super.onBindView()
  }
}