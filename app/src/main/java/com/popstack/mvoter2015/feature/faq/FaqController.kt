package com.popstack.mvoter2015.feature.faq

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.bluelinelabs.conductor.RouterTransaction
import com.popstack.mvoter2015.R
import com.popstack.mvoter2015.core.mvp.MvvmController
import com.popstack.mvoter2015.databinding.ControllerInfoBinding
import com.popstack.mvoter2015.domain.faq.model.FaqCategory
import com.popstack.mvoter2015.feature.HasRouter
import com.popstack.mvoter2015.feature.faq.ballot.BallotExampleController
import com.popstack.mvoter2015.helper.RecyclerViewMarginDecoration
import com.popstack.mvoter2015.helper.conductor.requireActivity
import com.popstack.mvoter2015.helper.conductor.requireActivityAsAppCompatActivity
import com.popstack.mvoter2015.helper.conductor.requireContext
import com.popstack.mvoter2015.helper.conductor.setSupportActionBar
import com.popstack.mvoter2015.helper.conductor.supportActionBar
import com.popstack.mvoter2015.paging.CommonLoadStateAdapter
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class FaqController : MvvmController<ControllerInfoBinding>() {

  private val viewModel: FaqViewModel by viewModels()

  override val bindingInflater: (LayoutInflater) -> ControllerInfoBinding =
    ControllerInfoBinding::inflate

  private val infoPagingAdapter by lazy {
    InfoRecyclerViewAdapter(
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

    binding.tvSelectedCategory.setOnClickListener {
      requireActivityAsAppCompatActivity().registerForActivityResult(
        FaqCategorySelectActivity.SelectFaqCategoryContract()
      ) { selectedCategory ->
        if (selectedCategory != null) {
          viewModel.handleSelectFaqCategory(selectedCategory)
          infoPagingAdapter.refresh()
        }
      }.launch(viewModel.selectedFaqCategory())
    }

    binding.btnRetry.setOnClickListener {
      infoPagingAdapter.retry()
    }

    binding.rvFaq.apply {
      adapter = infoPagingAdapter.withLoadStateHeaderAndFooter(
        header = CommonLoadStateAdapter(infoPagingAdapter::retry),
        footer = CommonLoadStateAdapter(infoPagingAdapter::retry)
      )
      layoutManager = LinearLayoutManager(requireContext())
      val dimen =
        requireContext().resources.getDimensionPixelSize(R.dimen.recycler_view_item_margin)
      addItemDecoration(RecyclerViewMarginDecoration(dimen, 1))
    }

    infoPagingAdapter.addLoadStateListener { loadStates ->
      val refreshLoadState = loadStates.refresh
      binding.rvFaq.isVisible = refreshLoadState is LoadState.NotLoading
      binding.progressBar.isVisible = refreshLoadState is LoadState.Loading
      binding.tvErrorMessage.isVisible = refreshLoadState is LoadState.Error
      binding.btnRetry.isVisible = refreshLoadState is LoadState.Error

      if (refreshLoadState is LoadState.Error) {
        binding.tvErrorMessage.text = refreshLoadState.error.message
      }
    }

    lifecycleScope.launch {
      viewModel.faqPagingFlow.collectLatest {
        infoPagingAdapter.submitData(lifecycle, it)
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
      viewModel.handleSelectFaqCategory(FaqCategory.GENERAL)
      infoPagingAdapter.refresh()
    }
  }

}