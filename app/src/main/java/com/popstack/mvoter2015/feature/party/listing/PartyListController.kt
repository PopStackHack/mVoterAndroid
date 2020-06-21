package com.popstack.mvoter2015.feature.party.listing

import android.view.LayoutInflater
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bluelinelabs.conductor.RouterTransaction
import com.popstack.mvoter2015.R
import com.popstack.mvoter2015.core.mvp.MvvmController
import com.popstack.mvoter2015.databinding.ControllerPartyListBinding
import com.popstack.mvoter2015.domain.party.model.PartyId
import com.popstack.mvoter2015.feature.party.detail.PartyDetailController
import com.popstack.mvoter2015.feature.party.listing.PartyListViewItemRecyclerViewAdapter.PartyListItemClickListener
import com.popstack.mvoter2015.helper.RecyclerViewMarginDecoration
import com.popstack.mvoter2015.paging.CommonLoadStateAdapter
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class PartyListController : MvvmController<ControllerPartyListBinding>(),
  PartyListItemClickListener {

  private val viewModel: PartyListViewModel by viewModels()

  override val bindingInflater: (LayoutInflater) -> ControllerPartyListBinding =
    ControllerPartyListBinding::inflate

  private val partyPagingAdapter by lazy {
    PartyListViewItemRecyclerViewAdapter(this)
  }

  override fun onBindView() {
    super.onBindView()

    binding.rvParty.apply {
      adapter = partyPagingAdapter.withLoadStateHeaderAndFooter(
        header = CommonLoadStateAdapter(partyPagingAdapter::retry),
        footer = CommonLoadStateAdapter(partyPagingAdapter::retry)
      )
      layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
      val dimen =
        context.resources.getDimensionPixelSize(R.dimen.recycler_view_item_margin)
      addItemDecoration(RecyclerViewMarginDecoration(dimen, 0))
    }

    binding.btnRetry.setOnClickListener {
      partyPagingAdapter.retry()
    }

    partyPagingAdapter.addLoadStateListener { loadStates ->
      val refreshLoadState = loadStates.refresh
      binding.rvParty.isVisible = refreshLoadState is LoadState.NotLoading
      binding.progressBar.isVisible = refreshLoadState is LoadState.Loading
      binding.tvErrorMessage.isVisible = refreshLoadState is LoadState.Error
      binding.btnRetry.isVisible = refreshLoadState is LoadState.Error

      if (refreshLoadState is LoadState.Error) {
        binding.tvErrorMessage.text = refreshLoadState.error.message
      }
    }

    lifecycleScope.launch {
      viewModel.partyPagingFlow.collectLatest {
        partyPagingAdapter.submitData(lifecycle, it)
      }
    }
  }

  override fun onItemClick(partyId: PartyId) {
    val partyDetailController = PartyDetailController.newInstance(partyId)
    router.pushController(RouterTransaction.with(partyDetailController))
  }

//  override fun subscribeToViewItemLiveData(viewItemLiveData: LiveData<List<PartyListViewItem>>) {
//    viewItemLiveData.observe(lifecycleOwner, Observer { partyListViewItemList ->
//      partyListAdapter.submitList(partyListViewItemList)
//    })
//  }

}