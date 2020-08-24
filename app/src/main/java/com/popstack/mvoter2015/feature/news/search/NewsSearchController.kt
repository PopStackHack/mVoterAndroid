package com.popstack.mvoter2015.feature.news.search

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.widget.SearchView
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.popstack.mvoter2015.R
import com.popstack.mvoter2015.core.mvp.MvvmController
import com.popstack.mvoter2015.databinding.ControllerNewsSearchBinding
import com.popstack.mvoter2015.domain.news.model.NewsId
import com.popstack.mvoter2015.helper.RecyclerViewMarginDecoration
import com.popstack.mvoter2015.helper.conductor.requireActivityAsAppCompatActivity
import com.popstack.mvoter2015.helper.conductor.requireContext
import com.popstack.mvoter2015.helper.extensions.showKeyboard
import com.popstack.mvoter2015.paging.CommonLoadStateAdapter
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class NewsSearchController : MvvmController<ControllerNewsSearchBinding>() {

  override val bindingInflater: (LayoutInflater) -> ControllerNewsSearchBinding = ControllerNewsSearchBinding::inflate

  private val searchPagingAdapter = NewsSearchPagingAdapter(this::onNewsItemClick)

  private var searchJob: Job? = null

  private val viewModel: NewsSearchViewModel by viewModels()

  @OptIn(ExperimentalPagingApi::class)
  override fun onBindView(savedViewState: Bundle?) {
    super.onBindView(savedViewState)
    requireActivityAsAppCompatActivity().setSupportActionBar(binding.toolBar)
    requireActivityAsAppCompatActivity().supportActionBar?.setDisplayHomeAsUpEnabled(true)

    binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
      override fun onQueryTextSubmit(query: String?): Boolean {
        if (query != null && query.isNotEmpty()) {
          search(query)
        }
        return true
      }

      override fun onQueryTextChange(newText: String?): Boolean {
        return true
      }

    })

//    binding.rvPlaceholder.apply {
//      adapter = placeholderAdapter
//      layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
//      val dimen =
//        context.resources.getDimensionPixelSize(R.dimen.recycler_view_item_margin)
//      addItemDecoration(RecyclerViewMarginDecoration(dimen, 1))
//    }

    binding.rvNews.apply {
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
      binding.rvNews.isVisible = refreshLoadState is LoadState.NotLoading
      binding.rvPlaceholder.isVisible = refreshLoadState is LoadState.Loading
      binding.tvErrorMessage.isVisible = refreshLoadState is LoadState.Error
      binding.btnRetry.isVisible = refreshLoadState is LoadState.Error
      if (viewModel.currentQueryValue != null) {
        binding.tvInstruction.isVisible = false
      }

      if (refreshLoadState is LoadState.Error) {
        binding.tvErrorMessage.text = refreshLoadState.error.message
      }
    }

    lifecycleScope.launch {
      delay(500)
      binding.searchView.showKeyboard()
    }

    lifecycleScope.launch {
      searchPagingAdapter.dataRefreshFlow.collect { isEmpty ->
        binding.tvEmpty.isVisible = isEmpty
        binding.tvEmpty.text = requireContext().getString(R.string.empty_list_search_news, viewModel.currentQueryValue
          ?: "")
      }
    }
  }

  private fun search(query: String) {
    // Make sure we cancel the previous job before creating a new one
    searchJob?.cancel()
    searchJob = lifecycleScope.launch {
      viewModel.search(query).collectLatest { pagingData ->
        searchPagingAdapter.submitData(pagingData)
      }
    }
  }

  private fun onNewsItemClick(id: NewsId, url: String) {
    CustomTabsIntent.Builder()
      .setToolbarColor(ContextCompat.getColor(requireContext(), R.color.accent))
      .build()
      .launchUrl(requireContext(), url.toUri())
  }

}