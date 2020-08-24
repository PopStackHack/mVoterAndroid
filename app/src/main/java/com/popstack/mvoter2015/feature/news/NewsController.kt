package com.popstack.mvoter2015.feature.news

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bluelinelabs.conductor.RouterTransaction
import com.popstack.mvoter2015.R
import com.popstack.mvoter2015.core.mvp.MvvmController
import com.popstack.mvoter2015.databinding.ControllerNewsBinding
import com.popstack.mvoter2015.domain.news.model.NewsId
import com.popstack.mvoter2015.feature.HasRouter
import com.popstack.mvoter2015.feature.news.search.NewsSearchController
import com.popstack.mvoter2015.helper.RecyclerViewMarginDecoration
import com.popstack.mvoter2015.helper.conductor.requireActivity
import com.popstack.mvoter2015.helper.conductor.requireContext
import com.popstack.mvoter2015.helper.conductor.setSupportActionBar
import com.popstack.mvoter2015.helper.conductor.supportActionBar
import com.popstack.mvoter2015.logging.HasTag
import com.popstack.mvoter2015.paging.CommonLoadStateAdapter
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class NewsController : MvvmController<ControllerNewsBinding>(), HasTag {

  override val tag: String = "NewsController"

  override val bindingInflater: (LayoutInflater) -> ControllerNewsBinding =
    ControllerNewsBinding::inflate

  private val viewModel: NewsViewModel by viewModels()

  private val newsPagingAdapter by lazy {
    NewsRecyclerViewAdapter(this::onNewsItemClick)
  }

  override fun onBindView(savedViewState: Bundle?) {
    super.onBindView(savedViewState)
    setSupportActionBar(binding.toolBar)
    supportActionBar()?.title = requireContext().getString(R.string.navigation_news)

    setHasOptionsMenu(R.menu.menu_news, this::handleMenuItemClick)

    binding.rvNews.apply {
      adapter = newsPagingAdapter.withLoadStateFooter(
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
      viewModel.getNewsPagingFlow().collectLatest { pagingData ->
        newsPagingAdapter.submitData(lifecycle, pagingData)
      }
    }
  }

  private fun onNewsItemClick(id: NewsId, url: String) {
    CustomTabsIntent.Builder()
      .setToolbarColor(ContextCompat.getColor(requireContext(), R.color.accent))
      .build()
      .launchUrl(requireContext(), url.toUri())
  }

  private fun handleMenuItemClick(menuItem: MenuItem): Boolean {
    return when (menuItem.itemId) {
      R.id.action_search -> {
        if (requireActivity() is HasRouter) {
          (requireActivity() as HasRouter).router()
            .pushController(RouterTransaction.with(NewsSearchController()))
        }
        true
      }
      else -> false
    }
  }

}