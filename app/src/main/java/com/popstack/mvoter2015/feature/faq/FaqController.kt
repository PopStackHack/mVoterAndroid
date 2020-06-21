package com.popstack.mvoter2015.feature.faq

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.widget.AdapterView
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.popstack.mvoter2015.R
import com.popstack.mvoter2015.core.mvp.MvvmController
import com.popstack.mvoter2015.databinding.ControllerInfoBinding
import com.popstack.mvoter2015.helper.RecyclerViewMarginDecoration
import com.popstack.mvoter2015.helper.conductor.requireContext
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

  private val faqCategorySpinnerAdapter = FaqCategorySpinnerAdapter()

  private fun navigateToBallotExample() {
    TODO("Not yet implemented")
  }

  override fun onBindView() {
    super.onBindView()

    binding.spinnerCategory.adapter = faqCategorySpinnerAdapter
    binding.spinnerCategory.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
      override fun onNothingSelected(p0: AdapterView<*>?) {
        //DO NOTHING
      }

      override fun onItemSelected(
        adapterView: AdapterView<*>?,
        view: View?,
        position: Int,
        id: Long
      ) {
        viewModel.handleSelectFaqCategory(faqCategorySpinnerAdapter.getItem(position))
        infoPagingAdapter.refresh()
      }

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

    viewModel.singleCommandLiveData.observe(lifecycleOwner, Observer { singleCommand ->
      when (singleCommand) {
        is FaqViewModel.SingleCommand.ShareFaq -> {
          val shareIntent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, singleCommand.shareUrl)
            type = "text/plain"
          }
          startActivity(Intent.createChooser(shareIntent, "Share Faq to.."))

        }
      }
    })
  }

}