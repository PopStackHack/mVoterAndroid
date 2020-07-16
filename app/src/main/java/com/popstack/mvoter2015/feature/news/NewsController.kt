package com.popstack.mvoter2015.feature.news

import android.view.LayoutInflater
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.popstack.mvoter2015.R
import com.popstack.mvoter2015.core.mvp.MvvmController
import com.popstack.mvoter2015.databinding.ControllerNewsBinding
import com.popstack.mvoter2015.domain.news.model.NewsId
import com.popstack.mvoter2015.helper.RecyclerViewMarginDecoration
import com.popstack.mvoter2015.helper.conductor.requireContext
import com.popstack.mvoter2015.helper.conductor.setSupportActionBar
import com.popstack.mvoter2015.helper.conductor.supportActionBar
import com.popstack.mvoter2015.paging.CommonLoadStateAdapter
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class NewsController : MvvmController<ControllerNewsBinding>() {

  override val bindingInflater: (LayoutInflater) -> ControllerNewsBinding =
    ControllerNewsBinding::inflate

  private val viewModel: NewsViewModel by viewModels()

  private val newsPagingAdapter by lazy {
    NewsRecyclerViewAdapter(this::onNewsItemClick)
  }

  override fun onBindView() {
    super.onBindView()
    setSupportActionBar(binding.toolBar)
    supportActionBar()?.title = requireContext().getString(R.string.navigation_news)

    binding.rvNews.apply {
      adapter = newsPagingAdapter.withLoadStateHeaderAndFooter(
        header = CommonLoadStateAdapter(newsPagingAdapter::retry),
        footer = CommonLoadStateAdapter(newsPagingAdapter::retry)
      )
      layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
      val dimen =
        context.resources.getDimensionPixelSize(R.dimen.recycler_view_item_margin)
      addItemDecoration(RecyclerViewMarginDecoration(dimen, 0))
    }

    binding.btnRetry.setOnClickListener {
      newsPagingAdapter.retry()
    }

    newsPagingAdapter.addLoadStateListener { loadStates ->
      val refreshLoadState = loadStates.refresh
      binding.rvNews.isVisible = refreshLoadState is LoadState.NotLoading
      binding.progressBar.isVisible = refreshLoadState is LoadState.Loading
      binding.tvErrorMessage.isVisible = refreshLoadState is LoadState.Error
      binding.btnRetry.isVisible = refreshLoadState is LoadState.Error

      if (refreshLoadState is LoadState.Error) {
        binding.tvErrorMessage.text = refreshLoadState.error.message
      }
    }

    lifecycleScope.launch {
      viewModel.newsPagingFlow.collectLatest {
        newsPagingAdapter.submitData(lifecycle, it)
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