package com.popstack.mvoter2015.feature.candidate.listing

import android.view.LayoutInflater
import androidx.viewbinding.ViewBinding
import com.popstack.mvoter2015.core.mvp.MvpFragment
import com.popstack.mvoter2015.databinding.FragmentCandidateListBinding
import com.popstack.mvoter2015.feature.party.listing.PartyListView
import com.popstack.mvoter2015.feature.party.listing.PartyListViewModel
import javax.inject.Inject

class CandidateListFragment : MvpFragment<FragmentCandidateListBinding, CandidateListView, CandidateListViewModel>(),
    CandidateListView {

  override val viewModel: CandidateListViewModel by contractedViewModels()

  override val bindingInflater: (LayoutInflater) -> ViewBinding =
    FragmentCandidateListBinding::inflate

}