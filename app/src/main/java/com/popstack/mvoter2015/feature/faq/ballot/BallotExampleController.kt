package com.popstack.mvoter2015.feature.faq.ballot

import android.os.Bundle
import android.view.LayoutInflater
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import com.google.android.material.tabs.TabLayoutMediator
import com.popstack.mvoter2015.core.mvp.MvvmController
import com.popstack.mvoter2015.databinding.ControllerBallotExampleBinding
import com.popstack.mvoter2015.helper.asyncviewstate.AsyncViewState
import com.popstack.mvoter2015.helper.conductor.setSupportActionBar
import com.popstack.mvoter2015.helper.conductor.supportActionBar

class BallotExampleController : MvvmController<ControllerBallotExampleBinding>() {

  override val bindingInflater: (LayoutInflater) -> ControllerBallotExampleBinding =
    ControllerBallotExampleBinding::inflate

  private val viewModel: BallotExampleViewModel by viewModels()

  private val ballotAdapter by lazy {
    BallotExampleRecyclerViewAdapter()
  }

  override fun onBindView(savedViewState: Bundle?) {
    super.onBindView(savedViewState)

    setSupportActionBar(binding.toolBar)
    supportActionBar()?.title = ""
    supportActionBar()?.setDisplayHomeAsUpEnabled(true)

    binding.viewPager.apply {
      adapter = ballotAdapter
    }

    TabLayoutMediator(binding.tabLayoutIndicator, binding.viewPager) { tab, position ->
      //DO NOTHING
    }.attach()

    binding.btnRetry.setOnClickListener {
      viewModel.loadData()
    }

    viewModel.ballotViewItemLiveData.observe(this, Observer { viewState ->
      binding.viewPager.isVisible = viewState is AsyncViewState.Success
      binding.tabLayoutIndicator.isVisible = viewState is AsyncViewState.Success
      binding.progressBar.isVisible = viewState is AsyncViewState.Loading
      binding.tvErrorMessage.isVisible = viewState is AsyncViewState.Error
      binding.btnRetry.isVisible = viewState is AsyncViewState.Error

      if (viewState is AsyncViewState.Success) {
        ballotAdapter.submitList(viewState.value)
      } else if (viewState is AsyncViewState.Error) {
        binding.tvErrorMessage.text = viewState.errorMessage
      }
    })

    if (savedViewState == null) {
      viewModel.loadData()
    }
  }
}