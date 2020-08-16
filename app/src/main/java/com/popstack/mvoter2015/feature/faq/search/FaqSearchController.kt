package com.popstack.mvoter2015.feature.faq.search

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.popstack.mvoter2015.R
import com.popstack.mvoter2015.core.mvp.MvvmController
import com.popstack.mvoter2015.databinding.ControllerFaqSearchBinding
import com.popstack.mvoter2015.domain.faq.model.FaqId
import com.popstack.mvoter2015.helper.RecyclerViewMarginDecoration
import com.popstack.mvoter2015.helper.conductor.requireActivityAsAppCompatActivity
import com.popstack.mvoter2015.helper.conductor.requireContext
import com.popstack.mvoter2015.helper.extensions.showKeyboard
import com.popstack.mvoter2015.paging.CommonLoadStateAdapter
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import timber.log.Timber

class FaqSearchController : MvvmController<ControllerFaqSearchBinding>() {

  override val bindingInflater: (LayoutInflater) -> ControllerFaqSearchBinding = ControllerFaqSearchBinding::inflate

  private val viewModel: FaqSearchViewModel by viewModels()

  private val searchPagingAdapter = FaqSearchPagingAdapter(this@FaqSearchController::share)

  private val placeHolderAdapter = FaqSearchPlaceholderRecyclerViewAdapter()

  @OptIn(ExperimentalPagingApi::class)
  override fun onBindView(savedViewState: Bundle?) {
    super.onBindView(savedViewState)
    requireActivityAsAppCompatActivity().setSupportActionBar(binding.toolBar)
    requireActivityAsAppCompatActivity().supportActionBar?.setDisplayHomeAsUpEnabled(true)

    binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
      override fun onQueryTextSubmit(query: String?): Boolean {
        search(query ?: "")
        binding.tvInstruction.isVisible = false
        return true
      }

      override fun onQueryTextChange(newText: String?): Boolean {
        return true
      }

    })

    binding.rvFaqPlaceholder.apply {
      adapter = placeHolderAdapter
      layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
      val dimen =
        context.resources.getDimensionPixelSize(R.dimen.recycler_view_item_margin)
      addItemDecoration(RecyclerViewMarginDecoration(dimen, 1))
    }

    binding.rvFaq.apply {
      adapter = searchPagingAdapter.withLoadStateHeaderAndFooter(
        header = CommonLoadStateAdapter(searchPagingAdapter::retry),
        footer = CommonLoadStateAdapter(searchPagingAdapter::retry)
      )
      layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
      val dimen =
        context.resources.getDimensionPixelSize(R.dimen.recycler_view_item_margin)
      addItemDecoration(RecyclerViewMarginDecoration(dimen, 1))
    }

    binding.btnRetry.setOnClickListener {
      searchPagingAdapter.retry()
    }

    searchPagingAdapter.addLoadStateListener { loadStates ->
      val refreshLoadState = loadStates.refresh
      binding.rvFaq.isVisible = refreshLoadState is LoadState.NotLoading
      binding.rvFaqPlaceholder.isVisible = refreshLoadState is LoadState.Loading
      binding.tvErrorMessage.isVisible = refreshLoadState is LoadState.Error
      binding.btnRetry.isVisible = refreshLoadState is LoadState.Error
      if (viewModel.currentQueryValue != null) {
        binding.tvInstruction.isVisible = false
      }

      if (refreshLoadState is LoadState.Error) {
        binding.tvErrorMessage.text = refreshLoadState.error.message
      }
    }

    viewModel.viewEventLiveData.observe(this, Observer(this::observeSingleEvent))

    binding.searchView.post {
      binding.searchView.showKeyboard()
    }

    lifecycleScope.launch {
      searchPagingAdapter.dataRefreshFlow.collect { isEmpty ->
        Timber.i("isEmpty : $isEmpty")
        binding.tvEmpty.isVisible = isEmpty
        binding.tvEmpty.text = requireContext().getString(R.string.empty_list_search_faq, viewModel.currentQueryValue
          ?: "")
      }
    }
  }

  private fun observeSingleEvent(singleEvent: FaqSearchViewModel.SingleEvent) {
    when (singleEvent) {
      is FaqSearchViewModel.SingleEvent.ShareFaq -> {
        val shareIntent = Intent().apply {
          action = Intent.ACTION_SEND
          putExtra(Intent.EXTRA_TEXT, singleEvent.shareUrl)
          type = "text/plain"
        }
        startActivity(Intent.createChooser(shareIntent, "Share Faq to.."))
      }
    }

  }

  private var searchJob: Job? = null

  private fun search(query: String) {
    // Make sure we cancel the previous job before creating a new one
    searchJob?.cancel()
    searchJob = lifecycleScope.launch {
      viewModel.search(query).collectLatest { pagingData ->
        searchPagingAdapter.submitData(pagingData)
      }
    }
  }

  private fun share(faqId: FaqId, position: Int) {
    viewModel.handleShareClick(faqId)
  }

}