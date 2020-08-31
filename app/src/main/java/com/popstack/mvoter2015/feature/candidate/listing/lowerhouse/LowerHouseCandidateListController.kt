package com.popstack.mvoter2015.feature.candidate.listing.lowerhouse

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.popstack.mvoter2015.core.mvp.MvvmController
import com.popstack.mvoter2015.databinding.ControllerLowerHouseCandidateListBinding
import com.popstack.mvoter2015.di.conductor.ConductorInjection
import com.popstack.mvoter2015.logging.HasTag

class LowerHouseCandidateListController :
  MvvmController<ControllerLowerHouseCandidateListBinding>(), HasTag {

  override val tag: String = "LowerHouseCandidateListController"

  private val viewModel: LowerHouseCandidateListViewModel by viewModels()

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