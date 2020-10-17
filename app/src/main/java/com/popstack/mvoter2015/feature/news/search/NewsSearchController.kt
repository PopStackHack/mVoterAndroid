package com.popstack.mvoter2015.feature.news.search

import android.os.Bundle
import android.view.LayoutInflater
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.popstack.mvoter2015.R
import com.popstack.mvoter2015.core.mvp.MvvmController
import com.popstack.mvoter2015.databinding.ControllerNewsSearchBinding
import com.popstack.mvoter2015.domain.news.model.NewsId
import com.popstack.mvoter2015.exception.GlobalExceptionHandler
import com.popstack.mvoter2015.feature.analytics.screen.CanTrackScreen
import com.popstack.mvoter2015.feature.browser.OpenBrowserDelegate
import com.popstack.mvoter2015.helper.RecyclerViewMarginDecoration
import com.popstack.mvoter2015.helper.conductor.requireActivity
import com.popstack.mvoter2015.helper.conductor.requireActivityAsAppCompatActivity
import com.popstack.mvoter2015.helper.conductor.requireContext
import com.popstack.mvoter2015.helper.extensions.isLandScape
import com.popstack.mvoter2015.helper.extensions.isTablet
import com.popstack.mvoter2015.helper.extensions.showKeyboard
import com.popstack.mvoter2015.helper.search.DebounceSearchQueryListener
import com.popstack.mvoter2015.logging.HasTag
import com.popstack.mvoter2015.paging.CommonLoadStateAdapter
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

class NewsSearchController : MvvmController<ControllerNewsSearchBinding>(), HasTag, CanTrackScreen {

  override val tag: String = "NewsSearchController"

  override val screenName: String = "NewsSearchController"

  override val bindingInflater: (LayoutInflater) -> ControllerNewsSearchBinding =
    ControllerNewsSearchBinding::inflate

  private val searchPagingAdapter = NewsSearchPagingAdapter(this::onNewsItemClick)

  private var searchJob: Job? = null

  private val viewModel: NewsSearchViewModel by viewModels()

  private val globalExceptionHandler by lazy {
    GlobalExceptionHandler(requireContext())
  }

  @Inject
  lateinit var browserDelegate: OpenBrowserDelegate

  @OptIn(ExperimentalPagingApi::class)
  override fun onBindView(savedViewState: Bundle?) {
    super.onBindView(savedViewState)
    requireActivityAsAppCompatActivity().setSupportActionBar(binding.toolBar)
    requireActivityAsAppCompatActivity().supportActionBar?.setDisplayHomeAsUpEnabled(true)

    binding.searchView.setOnQueryTextListener(
      DebounceSearchQueryListener(
        onQuery = { query -> search(query) },
        scope = lifecycleScope
      )
    )

    binding.rvNews.apply {
      adapter = searchPagingAdapter.withLoadStateHeaderAndFooter(
        header = CommonLoadStateAdapter(searchPagingAdapter::retry),
        footer = CommonLoadStateAdapter(searchPagingAdapter::retry)
      )
      layoutManager = if (requireContext().isTablet() && requireContext().isLandScape()) {
        GridLayoutManager(requireContext(), 2)
      } else {
        LinearLayoutManager(requireContext())
      }
      val dimen = context.resources.getDimensionPixelSize(R.dimen.recycler_view_item_margin)
      if (requireContext().isTablet() && requireContext().isLandScape()) {
        addItemDecoration(RecyclerViewMarginDecoration(dimen, 2))
      } else {
        addItemDecoration(RecyclerViewMarginDecoration(dimen, 0))
      }
    }

    binding.btnRetry.setOnClickListener {
      searchPagingAdapter.retry()
    }

    searchPagingAdapter.addLoadStateListener { loadStates ->
      val refreshLoadState = loadStates.refresh
      binding.rvNews.isVisible = refreshLoadState is LoadState.NotLoading
      if (refreshLoadState is LoadState.Loading) binding.progressIndicator.show()
      else binding.progressIndicator.hide()
      binding.tvErrorMessage.isVisible = refreshLoadState is LoadState.Error
      binding.btnRetry.isVisible = refreshLoadState is LoadState.Error
      if (viewModel.currentQueryValue != null) {
        binding.tvInstruction.isVisible = false
      }

      if (viewModel.currentQueryValue != null && refreshLoadState is LoadState.NotLoading && searchPagingAdapter.itemCount == 0) {
        binding.tvInstruction.isVisible = false
        binding.tvEmpty.isVisible = true
        binding.tvEmpty.text = requireContext().getString(
          R.string.empty_list_search_news,
          viewModel.currentQueryValue ?: ""
        )
      } else {
        binding.tvEmpty.isVisible = false
      }

      if (refreshLoadState is LoadState.Error) {
        binding.tvErrorMessage.text =
          globalExceptionHandler.getMessageForUser(refreshLoadState.error)
      }
    }

    binding.searchView.post {
      binding.searchView.showKeyboard()
    }
  }

  private fun search(query: String?) {
    // Make sure we cancel the previous job before creating a new one
    searchJob?.cancel()
    searchJob = lifecycleScope.launch {
      viewModel.search(query)
        .collectLatest { pagingData ->
          searchPagingAdapter.submitData(pagingData)
        }
    }
  }

  private fun onNewsItemClick(
    id: NewsId,
    url: String
  ) {
    lifecycleScope.launch {
      browserDelegate.browserHandler()
        .launchInBrowser(requireActivity(), url)
    }
  }

}