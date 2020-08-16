package com.popstack.mvoter2015.feature.faq

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.bluelinelabs.conductor.RouterTransaction
import com.popstack.mvoter2015.R
import com.popstack.mvoter2015.core.mvp.MvvmController
import com.popstack.mvoter2015.databinding.ControllerFaqBinding
import com.popstack.mvoter2015.domain.faq.model.FaqCategory
import com.popstack.mvoter2015.feature.HasRouter
import com.popstack.mvoter2015.feature.faq.ballot.BallotExampleController
import com.popstack.mvoter2015.feature.faq.search.FaqSearchController
import com.popstack.mvoter2015.helper.RecyclerViewMarginDecoration
import com.popstack.mvoter2015.helper.conductor.requireActivity
import com.popstack.mvoter2015.helper.conductor.requireActivityAsAppCompatActivity
import com.popstack.mvoter2015.helper.conductor.requireContext
import com.popstack.mvoter2015.helper.conductor.setSupportActionBar
import com.popstack.mvoter2015.helper.conductor.supportActionBar
import com.popstack.mvoter2015.paging.CommonLoadStateAdapter
import com.popstack.mvoter2015.logging.HasTag
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class FaqController : MvvmController<ControllerFaqBinding>(), HasTag {

  override val tag: String = "FaqController"

  private val viewModel: FaqViewModel by viewModels()

  override val bindingInflater: (LayoutInflater) -> ControllerFaqBinding =
    ControllerFaqBinding::inflate

  private val faqPagingAdapter by lazy {
    FaqPagingAdapter(
      ballotExampleClick = { navigateToBallotExample() },
      share = { faqId, _ ->
        viewModel.handleShareClick(faqId)
      }
    )
  }

  private fun navigateToBallotExample() {
    if (requireActivity() is HasRouter) {
      (requireActivity() as HasRouter).router()
        .pushController(RouterTransaction.with(BallotExampleController()))
    }
  }

  override fun onBindView(savedViewState: Bundle?) {
    super.onBindView(savedViewState)

    setSupportActionBar(binding.toolBar)
    supportActionBar()?.title = requireContext().getString(R.string.title_info)

    setHasOptionsMenu(R.menu.menu_faq, this@FaqController::handleMenuItemClick)
    binding.tvSelectedCategory.setOnClickListener {
      val selectFaqCategoryContract = requireActivityAsAppCompatActivity().registerForActivityResult(
        FaqCategorySelectActivity.SelectFaqCategoryContract()
      ) { selectedCategory ->
        if (selectedCategory != null) {
          selectFaqCategory(selectedCategory)
        }
      }
      viewModel.selectedFaqCategory()?.let {
        selectFaqCategoryContract.launch(it)
      }

    }

    binding.btnRetry.setOnClickListener {
      faqPagingAdapter.retry()
    }

    binding.rvFaqPlaceholder.apply {
      adapter = FaqPlaceholderRecyclerViewAdapter()
      layoutManager = LinearLayoutManager(requireContext())
      val dimen =
        requireContext().resources.getDimensionPixelSize(R.dimen.recycler_view_item_margin)
      addItemDecoration(RecyclerViewMarginDecoration(dimen, 1))
    }

    binding.rvFaq.apply {
      adapter = faqPagingAdapter.withLoadStateHeaderAndFooter(
        header = CommonLoadStateAdapter(faqPagingAdapter::retry),
        footer = CommonLoadStateAdapter(faqPagingAdapter::retry)
      )
      layoutManager = LinearLayoutManager(requireContext())
      val dimen =
        requireContext().resources.getDimensionPixelSize(R.dimen.recycler_view_item_margin)
      addItemDecoration(RecyclerViewMarginDecoration(dimen, 1))
    }

    faqPagingAdapter.addLoadStateListener { loadStates ->
      val refreshLoadState = loadStates.refresh
      binding.rvFaq.isVisible = refreshLoadState is LoadState.NotLoading
      binding.rvFaqPlaceholder.isVisible = refreshLoadState is LoadState.Loading
      binding.tvErrorMessage.isVisible = refreshLoadState is LoadState.Error
      binding.btnRetry.isVisible = refreshLoadState is LoadState.Error

      if (refreshLoadState is LoadState.Error) {
        binding.tvErrorMessage.text = refreshLoadState.error.message
      }
    }

    viewModel.faqCategoryLiveData.observe(lifecycleOwner, Observer { faqCategory ->
      binding.tvSelectedCategory.text = faqCategory.displayString(requireContext())
    })

    viewModel.viewEventLiveData.observe(lifecycleOwner, Observer { singleEvent ->
      when (singleEvent) {
        is FaqViewModel.SingleEvent.ShareFaq -> {
          val shareIntent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, singleEvent.shareUrl)
            type = "text/plain"
          }
          startActivity(Intent.createChooser(shareIntent, "Share Faq to.."))

        }
      }
    })

    if (savedViewState == null) {
      selectFaqCategory(FaqCategory.GENERAL)
      faqPagingAdapter.refresh()
    }
  }

  private var selectFaqJob: Job? = null

  private fun selectFaqCategory(faqCategory: FaqCategory) {
    selectFaqJob?.cancel()
    selectFaqJob = lifecycleScope.launch {
      viewModel.selectFaqCategory(faqCategory).collectLatest { pagingData ->
        faqPagingAdapter.submitData(pagingData)
      }
    }
  }

  private fun handleMenuItemClick(menuItem: MenuItem): Boolean {
    return when (menuItem.itemId) {
      R.id.action_search -> {
        if (requireActivity() is HasRouter) {
          (requireActivity() as HasRouter).router()
            .pushController(RouterTransaction.with(FaqSearchController()))
        }
        true
      }
      else -> false
    }
  }

}