package com.popstack.mvoter2015.feature.info

import android.content.Intent
import android.view.LayoutInflater
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.popstack.mvoter2015.R
import com.popstack.mvoter2015.core.mvp.MvvmController
import com.popstack.mvoter2015.databinding.ControllerInfoBinding
import com.popstack.mvoter2015.helper.RecyclerViewMarginDecoration
import com.popstack.mvoter2015.helper.conductor.requireContext
import com.popstack.mvoter2015.paging.CommonLoadStateAdapter
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class InfoController : MvvmController<ControllerInfoBinding>() {

  private val viewModel: InfoViewModel by viewModels()

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
    TODO("Not yet implemented")
  }

  override fun onBindView() {
    super.onBindView()

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

    lifecycleScope.launch {
      viewModel.faqPagingFlow.collectLatest {
        infoPagingAdapter.submitData(lifecycle, it)
      }
    }

    viewModel.singleCommandLiveData.observe(lifecycleOwner, Observer { singleCommand ->
      when (singleCommand) {
        is InfoViewModel.SingleCommand.ShareFaq -> {
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