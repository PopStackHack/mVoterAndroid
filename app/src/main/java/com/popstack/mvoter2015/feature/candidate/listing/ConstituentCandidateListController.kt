package com.popstack.mvoter2015.feature.candidate.listing

import android.os.Bundle
import android.view.LayoutInflater
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.popstack.mvoter2015.core.mvp.MvvmController
import com.popstack.mvoter2015.databinding.ControllerConstituentCandidateListBinding
import com.popstack.mvoter2015.domain.constituency.model.ConstituencyId
import com.popstack.mvoter2015.domain.constituency.model.HouseType
import com.popstack.mvoter2015.helper.asyncviewstate.AsyncViewState
import com.popstack.mvoter2015.helper.conductor.requireContext
import com.popstack.mvoter2015.logging.HasTag
import javax.inject.Inject

//class ConstituentCandidateListController(bundle: Bundle) :
//  MvvmController<ControllerConstituentCandidateListBinding>(bundle), HasTag {
//
//  companion object {
//    const val HOUSE_TYPE = "house_type"
//    const val CONSTITUENCY_ID = "constituency_id"
//
//    fun newInstance(constituencyId: ConstituencyId, houseType: HouseType) = ConstituentCandidateListController(
//      bundleOf(
//        CONSTITUENCY_ID to constituencyId.value,
//        HOUSE_TYPE to houseType.name
//      )
//    )
//  }
//
//  override val tag: String = "ConstituentCandidateListController"
//
//  private val viewModel: ConstituentCandidateListViewModel by viewModels()
//
//  override val bindingInflater: (LayoutInflater) -> ControllerConstituentCandidateListBinding =
//    ControllerConstituentCandidateListBinding::inflate
//
//  private val candidateListAdapter by lazy {
//    CandidateListRecyclerViewAdapter()
//  }
//
//  private val constituencyId: ConstituencyId = ConstituencyId(args.getString(CONSTITUENCY_ID)!!)
//  private val houseType: HouseType = HouseType.valueOf(args.getString(HOUSE_TYPE)!!)
//
//  override fun onBindView(savedViewState: Bundle?) {
//    super.onBindView(savedViewState)
//    binding.rvCandidate.apply {
//      adapter = candidateListAdapter
//      layoutManager = LinearLayoutManager(requireContext())
//    }
//
//    viewModel.viewItemLiveData.observe(this, Observer(::observeViewItem))
//
//    binding.btnRetry.setOnClickListener {
//      loadCandidates()
//    }
//
//    if (savedViewState == null) {
//      loadCandidates()
//    }
//  }
//
//  private fun loadCandidates() {
//    viewModel.loadCandidates(constituencyId, houseType)
//  }
//
//  private fun observeViewItem(viewState: AsyncViewState<CandidateListViewItem>) = with(binding) {
//    progressBar.isVisible = viewState is AsyncViewState.Loading
//    rvCandidate.isVisible = viewState is AsyncViewState.Success
//    tvErrorMessage.isVisible = viewState is AsyncViewState.Error
//    btnRetry.isVisible = viewState is AsyncViewState.Error
//
//    when (viewState) {
//      is AsyncViewState.Success -> {
//        candidateListAdapter.submitList(viewState.value.candidateList)
//      }
//      is AsyncViewState.Error -> {
//        tvErrorMessage.text = viewState.errorMessage
//      }
//    }
//  }
//
//}