package com.popstack.mvoter2015.feature.candidate.listing.regionalhouse

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.popstack.mvoter2015.core.mvp.MvvmController
import com.popstack.mvoter2015.databinding.ControllerRegionalCandidateListBinding
import com.popstack.mvoter2015.logging.HasTag

class RegionalHouseCandidateListController :
  MvvmController<ControllerRegionalCandidateListBinding>(), HasTag {

  override val tag: String = "RegionalHouseCandidateListController"

  private val viewModel: RegionalHouseCandidateListViewModel by viewModels()

  override val bindingInflater: (LayoutInflater) -> ControllerRegionalCandidateListBinding =
    ControllerRegionalCandidateListBinding::inflate

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup,
    savedViewState: Bundle?
  ): View {
    return super.onCreateView(inflater, container, savedViewState)
  }
}