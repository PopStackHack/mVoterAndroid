package com.popstack.mvoter2015.feature.candidate.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import androidx.viewbinding.ViewBinding
import com.popstack.mvoter2015.core.mvp.MvpFragment
import com.popstack.mvoter2015.databinding.FragmentCandidateDetailBinding
import com.popstack.mvoter2015.domain.candidate.model.CandidateId

class CandidateDetailFragment : MvpFragment<FragmentCandidateDetailBinding, CandidateDetailView, CandidateDetailViewModel>(),
  CandidateDetailView {

  override val viewModel: CandidateDetailViewModel by contractedViewModels()

  override val bindingInflater: (LayoutInflater) -> ViewBinding =
    FragmentCandidateDetailBinding::inflate

  private val navArg: CandidateDetailFragmentArgs by navArgs()

  override fun getCandidateId(): CandidateId = CandidateId(navArg.candidateId)

  override fun onBindView() {
    super.onBindView()
    val appBarConfiguration = AppBarConfiguration(findNavController().graph)
    binding.toolBar.setupWithNavController(findNavController(), appBarConfiguration)
  }

  override fun onViewCreated(
    view: View,
    savedInstanceState: Bundle?
  ) {
    super.onViewCreated(view, savedInstanceState)

    viewModel.loadData()
  }

}