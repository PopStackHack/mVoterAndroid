package com.popstack.mvoter2015.feature.candidate.listing.regionalhouse

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.bluelinelabs.conductor.RouterTransaction
import com.popstack.mvoter2015.R
import com.popstack.mvoter2015.core.mvp.MvvmController
import com.popstack.mvoter2015.databinding.ControllerRegionalCandidateListBinding
import com.popstack.mvoter2015.di.conductor.ConductorInjection
import com.popstack.mvoter2015.domain.candidate.model.CandidateId
import com.popstack.mvoter2015.domain.constituency.model.ConstituencyId
import com.popstack.mvoter2015.domain.constituency.model.HouseType
import com.popstack.mvoter2015.feature.candidate.detail.CandidateDetailController
import com.popstack.mvoter2015.feature.candidate.listing.CandidateListPagerParentRouter
import com.popstack.mvoter2015.feature.candidate.listing.CandidateListRecyclerViewAdapter
import com.popstack.mvoter2015.feature.candidate.listing.CandidateListViewItem
import com.popstack.mvoter2015.feature.home.BottomNavigationHostViewModelStore
import com.popstack.mvoter2015.helper.asyncviewstate.AsyncViewState
import com.popstack.mvoter2015.helper.conductor.requireContext
import com.popstack.mvoter2015.logging.HasTag

class RegionalHouseCandidateListController(bundle: Bundle) :
  MvvmController<ControllerRegionalCandidateListBinding>(bundle), HasTag {

  companion object {
    const val HOUSE_TYPE = "house_type"
    const val CONSTITUENCY_ID = "constituency_id"

    fun newInstance(constituencyId: ConstituencyId, houseType: HouseType) = RegionalHouseCandidateListController(
      bundleOf(
        CONSTITUENCY_ID to constituencyId.value,
        HOUSE_TYPE to houseType.name
      )
    )
  }

  override val tag: String = "RegionalHouseCandidateListController"

  private val viewModel: RegionalHouseCandidateListViewModel by viewModels(
    store = BottomNavigationHostViewModelStore.viewModelStore ?: viewModelStore
  )

  override val bindingInflater: (LayoutInflater) -> ControllerRegionalCandidateListBinding =
    ControllerRegionalCandidateListBinding::inflate

  private val candidateListAdapter by lazy {
    CandidateListRecyclerViewAdapter(onCandidateClicked)
  }

  private val onCandidateClicked: (CandidateId) -> Unit = {
    val candidateDetailsController = CandidateDetailController.newInstance(it)
    CandidateListPagerParentRouter.router?.pushController(RouterTransaction.with(candidateDetailsController))
  }

  private val constituencyId: ConstituencyId = ConstituencyId(args.getString(CONSTITUENCY_ID)!!)
  private val houseType: HouseType = HouseType.valueOf(args.getString(HOUSE_TYPE)!!)

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup, savedViewState: Bundle?): View {
    ConductorInjection.inject(this)
    return super.onCreateView(inflater, container, savedViewState)
  }

  override fun onBindView(savedViewState: Bundle?) {
    super.onBindView(savedViewState)
    binding.rvCandidate.apply {
      adapter = candidateListAdapter
      layoutManager = LinearLayoutManager(requireContext())
    }

    viewModel.viewItemLiveData.observe(this, Observer(::observeViewItem))

    binding.btnRetry.setOnClickListener {
      loadCandidates()
    }

    if (savedViewState == null) {
      loadCandidates()
    }
  }

  private fun loadCandidates() {
    viewModel.loadCandidates(constituencyId, houseType)
  }

  private fun observeViewItem(viewState: AsyncViewState<CandidateListViewItem>) = with(binding) {
    progressBar.isVisible = viewState is AsyncViewState.Loading
    rvCandidate.isVisible = viewState is AsyncViewState.Success
    tvErrorMessage.isVisible = viewState is AsyncViewState.Error
    btnRetry.isVisible = viewState is AsyncViewState.Error

    when (viewState) {
      is AsyncViewState.Success -> {
        if (viewState.value.candidateList.isNotEmpty()) {
          candidateListAdapter.submitList(viewState.value.candidateList)
        } else {
          tvErrorMessage.isVisible = true
          tvErrorMessage.setText(R.string.error_server_404)
        }
      }
      is AsyncViewState.Error -> {
        tvErrorMessage.text = viewState.errorMessage
      }
    }
  }

}