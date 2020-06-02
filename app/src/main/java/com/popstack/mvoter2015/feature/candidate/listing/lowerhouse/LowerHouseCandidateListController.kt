package com.popstack.mvoter2015.feature.candidate.listing.lowerhouse

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.popstack.mvoter2015.core.mvp.MvpController
import com.popstack.mvoter2015.databinding.ControllerLowerHouseCandidateListBinding
import com.popstack.mvoter2015.databinding.ControllerRegionalCandidateListBinding
import com.popstack.mvoter2015.di.conductor.ConductorInjection

class LowerHouseCandidateListController :
  MvpController<ControllerLowerHouseCandidateListBinding, LowerHouseCandidateListView, LowerHouseCandidateListViewModel>(),
  LowerHouseCandidateListView {

  override val viewModel: LowerHouseCandidateListViewModel by contractedViewModels()

  override val bindingInflater: (LayoutInflater) -> ControllerLowerHouseCandidateListBinding =
    ControllerLowerHouseCandidateListBinding::inflate

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup,
    savedViewState: Bundle?
  ): View {
    ConductorInjection.inject(this)
    return super.onCreateView(inflater, container, savedViewState)
  }

}