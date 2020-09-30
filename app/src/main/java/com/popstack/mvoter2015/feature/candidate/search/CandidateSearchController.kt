package com.popstack.mvoter2015.feature.candidate.search

import android.os.Bundle
import android.view.LayoutInflater
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bluelinelabs.conductor.RouterTransaction
import com.popstack.mvoter2015.R
import com.popstack.mvoter2015.core.mvp.MvvmController
import com.popstack.mvoter2015.databinding.ControllerCandidateSearchBinding
import com.popstack.mvoter2015.domain.candidate.model.CandidateId
import com.popstack.mvoter2015.exception.GlobalExceptionHandler
import com.popstack.mvoter2015.feature.analytics.screen.CanTrackScreen
import com.popstack.mvoter2015.feature.candidate.detail.CandidateDetailController
import com.popstack.mvoter2015.helper.RecyclerViewMarginDecoration
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

class CandidateSearchController : MvvmController<ControllerCandidateSearchBinding>(), HasTag, CanTrackScreen {

  override val tag: String = "CandidateSearchController"

  override val screenName: String = "CandidateSearchController"

  private val viewModel: CandidateSearchViewModel by viewModels()

  override val bindingInflater: (LayoutInflater) -> ControllerCandidateSearchBinding =
    ControllerCandidateSearchBinding::inflate

  private val searchPagingAdapter = CandidateSearchPagingAdapter(this::onItemClick)

  private var searchJob: Job? = null

  private val globalExceptionHandler by lazy {
    GlobalExceptionHandler(requireContext())
  }

  @OptIn(ExperimentalPagingApi::class)
  override fun onBindView(savedViewState: Bundle?) {
    super.onBindView(savedViewState)
    requireActivityAsAppCompatActivity().setSupportActionBar(binding.toolBar)
    requireActivityAsAppCompatActivity().supportActionBar?.setDisplayHomeAsUpEnabled(true)

    binding.searchView.setOnQueryTextListener(
      DebounceSearchQueryListener(
        onQuery = { query ->
          search(query)
        },
        scope = lifecycleScope
      )
    )

    binding.rvCandidate.apply {
      adapter = searchPagingAdapter.withLoadStateHeaderAndFooter(
        header = CommonLoadStateAdapter(searchPagingAdapter::retry),
        footer = CommonLoadStateAdapter(searchPagingAdapter::retry)
      )
      layoutManager = if (requireContext().isTablet() && requireContext().isLandScape()) {
        GridLayoutManager(requireContext(), 2)
      } else {
        LinearLayoutManager(requireContext())
      }
      val dimen =
        context.resources.getDimensionPixelSize(R.dimen.recycler_view_item_margin)
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
      binding.rvCandidate.isVisible = refreshLoadState is LoadState.NotLoading
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
          R.string.empty_list_search_candidate,
          viewModel.currentQueryValue ?: ""
        )
      } else {
        binding.tvEmpty.isVisible = false
      }

      if (refreshLoadState is LoadState.Error) {
        binding.tvErrorMessage.text = globalExceptionHandler.getMessageForUser(refreshLoadState.error)
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
      viewModel.search(query).collectLatest { pagingData ->
        searchPagingAdapter.submitData(pagingData)
      }
    }
  }

  private fun onItemClick(candidateId: CandidateId) {
    val candidateDetailController = CandidateDetailController.newInstance(candidateId)
    router.pushController(RouterTransaction.with(candidateDetailController))
  }

}