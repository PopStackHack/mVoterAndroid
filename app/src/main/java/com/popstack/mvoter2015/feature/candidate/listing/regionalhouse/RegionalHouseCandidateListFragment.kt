package com.popstack.mvoter2015.feature.candidate.listing.regionalhouse

import android.view.LayoutInflater
import androidx.viewbinding.ViewBinding
import com.popstack.mvoter2015.core.mvp.MvpFragment
import com.popstack.mvoter2015.databinding.FragmentRegionalCandidateListBinding

class RegionalHouseCandidateListFragment :
  MvpFragment<FragmentRegionalCandidateListBinding, RegionalHouseCandidateListView, RegionalHouseCandidateListViewModel>(),
  RegionalHouseCandidateListView {

  override val viewModel: RegionalHouseCandidateListViewModel by contractedViewModels()

  override val bindingInflater: (LayoutInflater) -> ViewBinding =
    FragmentRegionalCandidateListBinding::inflate
}