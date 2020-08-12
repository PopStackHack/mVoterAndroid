package com.popstack.mvoter2015.feature.faq.ballot

import android.os.Bundle
import android.view.LayoutInflater
import android.widget.EditText
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Observer
import com.popstack.mvoter2015.R
import com.popstack.mvoter2015.core.mvp.MvvmController
import com.popstack.mvoter2015.databinding.ControllerBallotExampleBinding
import com.popstack.mvoter2015.helper.asyncviewstate.AsyncViewState
import com.popstack.mvoter2015.helper.conductor.requireContext
import com.popstack.mvoter2015.helper.conductor.setSupportActionBar
import com.popstack.mvoter2015.helper.conductor.supportActionBar
import com.popstack.mvoter2015.helper.extensions.addOnTabSelectedListener
import com.popstack.mvoter2015.helper.extensions.forEachTab
import com.popstack.mvoter2015.sentry.HasTag

class BallotExampleController : MvvmController<ControllerBallotExampleBinding>(), HasTag {

  override val tag: String = "BallotExampleController"

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

    EditText(requireContext()).addTextChangedListener { }

    val inValidBallotTab = binding.tabLayoutValid.newTab().apply {
      setIcon(R.drawable.ic_close_circle_24)
      setText(R.string.invalid_ballot)
      icon?.setTint(ContextCompat.getColor(requireContext(), R.color.grey))
    }
    binding.tabLayoutValid.addTab(inValidBallotTab)

    val validBallotTab = binding.tabLayoutValid.newTab().apply {
      setIcon(R.drawable.ic_check_circle_24)
      setText(R.string.valid_ballot)
      icon?.setTint(ContextCompat.getColor(requireContext(), R.color.grey))
    }
    binding.tabLayoutValid.addTab(validBallotTab)

    binding.tabLayoutValid.addOnTabSelectedListener { tab ->
      if (tab == inValidBallotTab) {
        tab.icon?.setTint(ContextCompat.getColor(requireContext(), R.color.text_error))
      } else if (tab == validBallotTab) {
        tab.icon?.setTint(ContextCompat.getColor(requireContext(), R.color.green))
      }

      binding.tabLayoutValid.forEachTab {
        if (it != tab) {
          it.icon?.setTint(ContextCompat.getColor(requireContext(), R.color.grey))
        }
      }
    }

    binding.btnRetry.setOnClickListener {
      viewModel.loadData()
    }

    viewModel.ballotViewItemLiveData.observe(this, Observer { viewState ->
      binding.viewPager.isVisible = viewState is AsyncViewState.Success
//      binding.tabLayoutValid.isVisible = viewState is AsyncViewState.Success
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