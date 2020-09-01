package com.popstack.mvoter2015.feature.party.listing

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bluelinelabs.conductor.RouterTransaction
import com.popstack.mvoter2015.R
import com.popstack.mvoter2015.core.mvp.MvvmController
import com.popstack.mvoter2015.databinding.ControllerPartyListBinding
import com.popstack.mvoter2015.domain.party.model.PartyId
import com.popstack.mvoter2015.exception.GlobalExceptionHandler
import com.popstack.mvoter2015.feature.HasRouter
import com.popstack.mvoter2015.feature.home.BottomNavigationHostViewModelStore
import com.popstack.mvoter2015.feature.party.detail.PartyDetailController
import com.popstack.mvoter2015.feature.party.search.PartySearchController
import com.popstack.mvoter2015.helper.RecyclerViewMarginDecoration
import com.popstack.mvoter2015.helper.ViewVisibilityDebounceHandler
import com.popstack.mvoter2015.helper.conductor.requireActivity
import com.popstack.mvoter2015.helper.conductor.requireActivityAsAppCompatActivity
import com.popstack.mvoter2015.helper.conductor.requireContext
import com.popstack.mvoter2015.logging.HasTag
import com.popstack.mvoter2015.paging.CommonLoadStateAdapter
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class PartyListController : MvvmController<ControllerPartyListBinding>(), HasTag {

  override val tag: String = "PartyListController"

  private val viewModel: PartyListViewModel by viewModels(
    store = BottomNavigationHostViewModelStore.viewModelStore ?: viewModelStore
  )

  override val bindingInflater: (LayoutInflater) -> ControllerPartyListBinding =
    ControllerPartyListBinding::inflate

  private val partyPagingAdapter = PartyListViewItemRecyclerViewAdapter(this::onItemClick)

  private val globalExceptionHandler by lazy {
    GlobalExceptionHandler(requireContext())
  }

  override fun onBindView(savedViewState: Bundle?) {
    super.onBindView(savedViewState)
    requireActivityAsAppCompatActivity().setSupportActionBar(binding.toolBar)
    setHasOptionsMenu(R.menu.menu_party, this::handleMenuItemClick)
    binding.rvPlaceholder.apply {
      adapter = PartyPlaceHolderRecyclerViewAdapter()
      layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
      val dimen = context.resources.getDimensionPixelSize(R.dimen.recycler_view_item_margin)
      addItemDecoration(RecyclerViewMarginDecoration(dimen, 0))
    }

    binding.rvParty.apply {
      adapter = ConcatAdapter(
        PartyListHeaderRecycleViewAdapter(),
        partyPagingAdapter.withLoadStateFooter(
          footer = CommonLoadStateAdapter(partyPagingAdapter::retry)
        )
      )
      layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
      val dimen =
        context.resources.getDimensionPixelSize(R.dimen.recycler_view_item_margin)
      addItemDecoration(RecyclerViewMarginDecoration(dimen, 0))
    }

    binding.btnRetry.setOnClickListener {
      partyPagingAdapter.retry()
    }

    val placeHolderVisibilityHandler = ViewVisibilityDebounceHandler(binding.rvPlaceholder)

    partyPagingAdapter.addLoadStateListener { loadStates ->
      val refreshLoadState = loadStates.refresh
      binding.rvParty.isVisible = refreshLoadState is LoadState.NotLoading
      placeHolderVisibilityHandler.setVisible(refreshLoadState is LoadState.Loading)
      binding.tvErrorMessage.isVisible = refreshLoadState is LoadState.Error
      binding.btnRetry.isVisible = refreshLoadState is LoadState.Error

      if (refreshLoadState is LoadState.Error) {
        binding.tvErrorMessage.text = globalExceptionHandler.getMessageForUser(refreshLoadState.error)
      }
    }

    lifecycleScope.launch {
      viewModel.partyPagingFlow.collectLatest { pagingData ->
        partyPagingAdapter.submitData(lifecycle, pagingData)
      }
    }
  }

  private fun handleMenuItemClick(menuItem: MenuItem): Boolean {
    return when (menuItem.itemId) {
      R.id.action_search -> {
          router.pushController(RouterTransaction.with(PartySearchController()))
        true
      }
      else -> false
    }
  }

  private fun onItemClick(partyId: PartyId) {
    val partyDetailController = PartyDetailController.newInstance(partyId)
    router.pushController(RouterTransaction.with(partyDetailController))
//    if (requireActivity() is HasRouter) {
//      (requireActivity() as HasRouter).router().pushController(RouterTransaction.with(partyDetailController))
//    }
  }

}