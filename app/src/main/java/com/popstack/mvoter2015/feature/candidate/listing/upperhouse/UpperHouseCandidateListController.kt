package com.popstack.mvoter2015.feature.candidate.listing.upperhouse

import android.os.Bundle
import android.view.LayoutInflater
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.bluelinelabs.conductor.RouterTransaction
import com.popstack.mvoter2015.R
import com.popstack.mvoter2015.core.mvp.MvvmController
import com.popstack.mvoter2015.databinding.ControllerUpperHouseCandidateListBinding
import com.popstack.mvoter2015.domain.candidate.model.CandidateId
import com.popstack.mvoter2015.feature.candidate.detail.CandidateDetailController
import com.popstack.mvoter2015.feature.candidate.listing.CandidateListPagerParentRouter
import com.popstack.mvoter2015.feature.candidate.listing.CandidateListRecyclerViewAdapter
import com.popstack.mvoter2015.feature.candidate.listing.CandidateListResult
import com.popstack.mvoter2015.feature.home.BottomNavigationHostViewModelStore
import com.popstack.mvoter2015.helper.asyncviewstate.AsyncViewState
import com.popstack.mvoter2015.helper.conductor.requireContext
import com.popstack.mvoter2015.logging.HasTag

class UpperHouseCandidateListController : MvvmController<ControllerUpperHouseCandidateListBinding>(), HasTag {

  override val tag: String = "UpperHouseCandidateListController"

  private val viewModel: UpperHouseCandidateListViewModel by viewModels(
    store = BottomNavigationHostViewModelStore.viewModelStore ?: viewModelStore
  )

  override val bindingInflater: (LayoutInflater) -> ControllerUpperHouseCandidateListBinding =
    ControllerUpperHouseCandidateListBinding::inflate

  private val candidateListAdapter by lazy {
    CandidateListRecyclerViewAdapter(onCandidateClicked)
  }

  private val onCandidateClicked: (CandidateId) -> Unit = {
    val candidateDetailsController = CandidateDetailController.newInstance(it)
    CandidateListPagerParentRouter.router?.pushController(RouterTransaction.with(candidateDetailsController))
  }

  override fun onBindView(savedViewState: Bundle?) {
    super.onBindView(savedViewState)

    binding.tvConstituencyName.text = viewModel.data.constituencyName

    binding.rvCandidate.apply {
      adapter = candidateListAdapter
      layoutManager = LinearLayoutManager(requireContext())
    }

    viewModel.viewItemLiveData.observe(this, Observer(::observeViewItem))
    viewModel.viewEventLiveData.observe(this, Observer(::observeViewEvent))

    binding.btnRetry.setOnClickListener {
      loadCandidates()
    }

    if (viewModel.viewItemLiveData.value == null) {
      loadCandidates()
    }
  }

  private fun loadCandidates() {
    viewModel.loadCandidates()
  }

  private fun observeViewEvent(viewEvent: UpperHouseCandidateListViewModel.ViewEvent) {
    when (viewEvent) {
      is UpperHouseCandidateListViewModel.ViewEvent.ShowConstituencyName -> {
        binding.tvConstituencyName.text = viewEvent.constituencyName
      }
    }
  }

  private fun observeViewItem(viewState: AsyncViewState<CandidateListResult>) = with(binding) {
    progressBar.isVisible = viewState is AsyncViewState.Loading
    rvCandidate.isVisible = viewState is AsyncViewState.Success
    tvErrorMessage.isVisible = viewState is AsyncViewState.Error
    btnRetry.isVisible = viewState is AsyncViewState.Error

    when (viewState) {
      is AsyncViewState.Success -> {
        val successValue = viewState.value
        rvCandidate.isVisible = successValue is CandidateListResult.CandidateListViewItem
        groupRemark.isVisible = successValue is CandidateListResult.Remark

        if (successValue is CandidateListResult.Remark) {
          tvRemark.text = successValue.remarkMessage
        } else if (successValue is CandidateListResult.CandidateListViewItem) {
          if (successValue.candidateList.isNotEmpty()) {
            candidateListAdapter.submitList(successValue.candidateList)
          } else {
            tvErrorMessage.isVisible = true
            tvErrorMessage.setText(R.string.error_server_404)
          }
        }

      }
      is AsyncViewState.Error -> {
        tvErrorMessage.text = viewState.errorMessage
      }
    }
  }

}