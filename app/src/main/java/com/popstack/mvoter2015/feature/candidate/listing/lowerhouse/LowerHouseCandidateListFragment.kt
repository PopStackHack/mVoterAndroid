package com.popstack.mvoter2015.feature.candidate.listing.lowerhouse

import android.view.LayoutInflater
import androidx.viewbinding.ViewBinding
import com.popstack.mvoter2015.core.mvp.MvpFragment
import com.popstack.mvoter2015.databinding.FragmentLowerHouseCandidateListBinding
import com.popstack.mvoter2015.feature.candidate.listing.regionalhouse.RegionalHouseCandidateListView
import com.popstack.mvoter2015.feature.candidate.listing.regionalhouse.RegionalHouseCandidateListViewModel

class LowerHouseCandidateListFragment :
  MvpFragment<FragmentLowerHouseCandidateListBinding, LowerHouseCandidateListView, LowerHouseCandidateListViewModel>(),
  LowerHouseCandidateListView {

  override val viewModel: LowerHouseCandidateListViewModel by contractedViewModels()

  override val bindingInflater: (LayoutInflater) -> ViewBinding =
    FragmentLowerHouseCandidateListBinding::inflate


}