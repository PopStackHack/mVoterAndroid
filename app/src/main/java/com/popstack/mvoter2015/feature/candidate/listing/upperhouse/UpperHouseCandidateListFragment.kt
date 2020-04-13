package com.popstack.mvoter2015.feature.candidate.listing.upperhouse

import android.view.LayoutInflater
import androidx.viewbinding.ViewBinding
import com.popstack.mvoter2015.core.mvp.MvpFragment
import com.popstack.mvoter2015.databinding.FragmentUpperHouseCandidateListBinding
import com.popstack.mvoter2015.feature.candidate.listing.regionalhouse.RegionalHouseCandidateListView
import com.popstack.mvoter2015.feature.candidate.listing.regionalhouse.RegionalHouseCandidateListViewModel

class UpperHouseCandidateListFragment :
  MvpFragment<FragmentUpperHouseCandidateListBinding, UpperHouseCandidateListView, UpperHouseCandidateListViewModel>(),
  UpperHouseCandidateListView {

  override val viewModel: UpperHouseCandidateListViewModel by contractedViewModels()

  override val bindingInflater: (LayoutInflater) -> ViewBinding =
    FragmentUpperHouseCandidateListBinding::inflate


}