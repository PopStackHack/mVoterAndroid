package com.popstack.mvoter2015.feature.faq.search

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.popstack.mvoter2015.R
import com.popstack.mvoter2015.core.mvp.MvvmController
import com.popstack.mvoter2015.databinding.ControllerFaqSearchBinding
import com.popstack.mvoter2015.domain.faq.model.FaqId
import com.popstack.mvoter2015.exception.GlobalExceptionHandler
import com.popstack.mvoter2015.feature.analytics.screen.CanTrackScreen
import com.popstack.mvoter2015.feature.share.ShareUrlFactory
import com.popstack.mvoter2015.helper.RecyclerViewMarginDecoration
import com.popstack.mvoter2015.helper.conductor.requireActivityAsAppCompatActivity
import com.popstack.mvoter2015.helper.conductor.requireContext
import com.popstack.mvoter2015.helper.extensions.showKeyboard
import com.popstack.mvoter2015.helper.intent.Intents
import com.popstack.mvoter2015.helper.search.DebounceSearchQueryListener
import com.popstack.mvoter2015.logging.HasTag
import com.popstack.mvoter2015.paging.CommonLoadStateAdapter
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class FaqSearchController : MvvmController<ControllerFaqSearchBinding>(), HasTag, CanTrackScreen {

  override val tag: String = "FaqSearchController"

  override val screenName: String = "FaqSearchController"

  override val bindingInflater: (LayoutInflater) -> ControllerFaqSearchBinding = ControllerFaqSearchBinding::inflate

  private val viewModel: FaqSearchViewModel by viewModels()

  private val searchPagingAdapter = FaqSearchPagingAdapter(this@FaqSearchController::share)

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
        onQuery = { query -> search(query) },
        scope = lifecycleScope
      )
    )

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
          R.string.empty_list_search_faq,
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

  private var searchJob: Job? = null

  private fun search(query: String?) {
    // Make sure we cancel the previous job before creating a new one
    searchJob?.cancel()
    searchJob = lifecycleScope.launch {
      viewModel.search(query).collectLatest { pagingData ->
        searchPagingAdapter.submitData(pagingData)
      }
    }
  }

  private fun share(faqId: FaqId, position: Int) {
    val shareIntent = Intents.shareUrl(ShareUrlFactory().faq(faqId))
    startActivity(Intent.createChooser(shareIntent, "Share Faq To..."))
  }

}