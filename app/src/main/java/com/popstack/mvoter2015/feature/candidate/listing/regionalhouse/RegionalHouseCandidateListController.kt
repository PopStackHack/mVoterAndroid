package com.popstack.mvoter2015.feature.candidate.listing.regionalhouse

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.popstack.mvoter2015.core.mvp.MvpController
import com.popstack.mvoter2015.databinding.ControllerRegionalCandidateListBinding
import com.popstack.mvoter2015.di.conductor.ConductorInjection

class RegionalHouseCandidateListController :
  MvpController<ControllerRegionalCandidateListBinding, RegionalHouseCandidateListView, RegionalHouseCandidateListViewModel>(),
  RegionalHouseCandidateListView {

  override val viewModel: RegionalHouseCandidateListViewModel by contractedViewModels()

  override val bindingInflater: (LayoutInflater, ViewGroup) -> ControllerRegionalCandidateListBinding
    get() = { layoutInflater, viewGroup ->
      ControllerRegionalCandidateListBinding.inflate(layoutInflater, viewGroup, false)
    }

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup,
    savedViewState: Bundle?
  ): View {
    ConductorInjection.inject(this)
    return super.onCreateView(inflater, container, savedViewState)
  }
}