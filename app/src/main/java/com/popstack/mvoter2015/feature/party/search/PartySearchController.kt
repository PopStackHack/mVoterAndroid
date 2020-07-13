package com.popstack.mvoter2015.feature.party.search

import android.view.LayoutInflater
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bluelinelabs.conductor.RouterTransaction
import com.popstack.mvoter2015.R
import com.popstack.mvoter2015.core.mvp.MvvmController
import com.popstack.mvoter2015.databinding.ControllerPartySearchBinding
import com.popstack.mvoter2015.domain.party.model.PartyId
import com.popstack.mvoter2015.feature.party.detail.PartyDetailController
import com.popstack.mvoter2015.helper.RecyclerViewMarginDecoration
import com.popstack.mvoter2015.helper.conductor.requireActivityAsAppCompatActivity
import com.popstack.mvoter2015.helper.extensions.showKeyboard
import com.popstack.mvoter2015.paging.CommonLoadStateAdapter
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class PartySearchController : MvvmController<ControllerPartySearchBinding>() {

  private val viewModel: PartySearchViewModel by viewModels()

  override val bindingInflater: (LayoutInflater) -> ControllerPartySearchBinding =
    ControllerPartySearchBinding::inflate

  private val searchPagingAdapter = PartySearchPagingAdapter(this::onItemClick)

  override fun onBindView() {
    super.onBindView()
    requireActivityAsAppCompatActivity().setSupportActionBar(binding.toolBar)
    requireActivityAsAppCompatActivity().supportActionBar?.setDisplayHomeAsUpEnabled(true)

    binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
      override fun onQueryTextSubmit(query: String?): Boolean {
        viewModel.handleSearchQueryTextChange(query ?: "")
        return true
      }

      override fun onQueryTextChange(newText: String?): Boolean {
        viewModel.handleSearchQueryTextChange(newText ?: "")
        return true
      }

    })

    binding.rvParty.apply {
      adapter = searchPagingAdapter.withLoadStateHeaderAndFooter(
        header = CommonLoadStateAdapter(searchPagingAdapter::retry),
        footer = CommonLoadStateAdapter(searchPagingAdapter::retry)
      )
      layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
      val dimen =
        context.resources.getDimensionPixelSize(R.dimen.recycler_view_item_margin)
      addItemDecoration(RecyclerViewMarginDecoration(dimen, 0))
    }

    binding.btnRetry.setOnClickListener {
      searchPagingAdapter.retry()
    }

    searchPagingAdapter.addLoadStateListener { loadStates ->
      val refreshLoadState = loadStates.refresh
      binding.rvParty.isVisible = refreshLoadState is LoadState.NotLoading
      binding.progressBar.isVisible = refreshLoadState is LoadState.Loading
      binding.tvErrorMessage.isVisible = refreshLoadState is LoadState.Error
      binding.btnRetry.isVisible = refreshLoadState is LoadState.Error

      if (refreshLoadState is LoadState.Error) {
        binding.tvErrorMessage.text = refreshLoadState.error.message
      }
    }

    viewModel.queryEmptyLiveData.observe(this, Observer(this::observeEmptyQuery))
    viewModel.pagingAdapterRefreshSingleLiveEvent.observe(
      this,
      Observer { searchPagingAdapter.refresh() })

    lifecycleScope.launch {
      viewModel.searchResultPagingFlow.collectLatest {
        searchPagingAdapter.submitData(lifecycle, it)
      }
    }
    lifecycleScope.launch {
      delay(500)
      binding.searchView.showKeyboard()
    }
  }

  private fun observeEmptyQuery(isEmpty: Boolean) {
    binding.btnRetry.isVisible = false
    binding.tvErrorMessage.isVisible = isEmpty
    binding.tvErrorMessage.text = "မိမိရှာလိုသော ပါတီနာမည်ကို အပေါ်တွင်ရိုက်ထည့်ပါ"
  }

  private fun onItemClick(partyId: PartyId) {
    val partyDetailController = PartyDetailController.newInstance(partyId)
    router.pushController(RouterTransaction.with(partyDetailController))
  }

}