package com.popstack.mvoter2015.feature.candidate.listing.upperhouse

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bluelinelabs.conductor.RouterTransaction
import com.popstack.mvoter2015.R
import com.popstack.mvoter2015.core.mvp.MvpController
import com.popstack.mvoter2015.databinding.ControllerCandidateListBinding
import com.popstack.mvoter2015.databinding.ControllerUpperHouseCandidateListBinding
import com.popstack.mvoter2015.di.conductor.ConductorInjection
import com.popstack.mvoter2015.domain.candidate.model.CandidateId
import com.popstack.mvoter2015.feature.candidate.detail.CandidateDetailController
import com.popstack.mvoter2015.feature.candidate.listing.CandidateListPagerParentRouter
import com.popstack.mvoter2015.feature.candidate.listing.upperhouse.UpperHouseCandidateListRecyclerViewAdapter.UpperHouseCandidateListItemClickListener
import com.popstack.mvoter2015.helper.RecyclerViewMarginDecoration

class UpperHouseCandidateListController :
  MvpController<ControllerUpperHouseCandidateListBinding, UpperHouseCandidateListView, UpperHouseCandidateListViewModel>(),
  UpperHouseCandidateListView, UpperHouseCandidateListItemClickListener {

  override val viewModel: UpperHouseCandidateListViewModel by contractedViewModels()

  override val bindingInflater: (LayoutInflater) -> ControllerUpperHouseCandidateListBinding =
    ControllerUpperHouseCandidateListBinding::inflate

  private val candidateListAdapter by lazy {
    UpperHouseCandidateListRecyclerViewAdapter(this)
  }

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup,
    savedViewState: Bundle?
  ): View {
    ConductorInjection.inject(this)
    return super.onCreateView(inflater, container, savedViewState)
  }

  override fun onBindView() {
    super.onBindView()

    binding.rvUpperHouseCandidate.apply {
      adapter = candidateListAdapter
      layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
      val dimen =
        context.resources.getDimensionPixelSize(R.dimen.recycler_view_item_margin)
      addItemDecoration(RecyclerViewMarginDecoration(dimen, 1))
    }

    viewModel.loadData()

  }

  override fun subscribeToViewItemLiveData(viewItemLiveData: LiveData<List<UpperHouseCandidateListViewItem>>) {
    viewItemLiveData.observe(lifecycleOwner, Observer { viewItemList ->
      candidateListAdapter.submitList(viewItemList)
    })

  }

  override fun onItemClick(candidateId: CandidateId) {
    val candidateDetailController = CandidateDetailController.newInstance(candidateId)
    CandidateListPagerParentRouter.router?.pushController(
      RouterTransaction.with(candidateDetailController)
    )
  }

}