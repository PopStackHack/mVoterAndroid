package com.popstack.mvoter2015.feature.location

import android.os.Bundle
import android.view.LayoutInflater
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.popstack.mvoter2015.core.mvp.MvvmController
import com.popstack.mvoter2015.databinding.ControllerWardChooserBinding
import com.popstack.mvoter2015.feature.HasRouter
import com.popstack.mvoter2015.helper.asyncviewstate.AsyncViewState
import com.popstack.mvoter2015.helper.conductor.requireActivity
import com.popstack.mvoter2015.logging.HasTag

class WardChooserController(bundle: Bundle) : MvvmController<ControllerWardChooserBinding>(bundle), HasTag {

  companion object {
    const val ARG_STATE_REGION = "state_region"
    const val ARG_TOWNSHIP = "township"

    fun newInstance(stateRegion: String, township: String) = WardChooserController(
      bundleOf(
        ARG_STATE_REGION to stateRegion,
        ARG_TOWNSHIP to township
      )
    )
  }

  override val tag: String = "WardChooserController"

  private val stateRegion by lazy {
    args.getString(ARG_STATE_REGION)
  }

  private val township by lazy {
    args.getString(ARG_TOWNSHIP)
  }

  private val viewModel: WardChooserViewModel by viewModels()

  override val bindingInflater: (LayoutInflater) -> ControllerWardChooserBinding =
    ControllerWardChooserBinding::inflate

  private lateinit var wardRecyclerViewAdapter: WardRecyclerViewAdapter

  override fun onBindView(savedViewState: Bundle?) {
    super.onBindView(savedViewState)

    wardRecyclerViewAdapter = WardRecyclerViewAdapter(
      viewModel.onWardClicked
    )

    binding.rvWard.apply {
      adapter = wardRecyclerViewAdapter
      layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
    }

    binding.tvTownship.text = township

    binding.ivClose.setOnClickListener {
      if (requireActivity() is HasRouter) {
        (requireActivity() as HasRouter).router()
          .popCurrentController()
      }
    }

    viewModel.onWardChosenEvent.observe(
      this,
      { ward ->
        if (requireActivity() is HasRouter) {
          (requireActivity() as HasRouter).router()
            .popCurrentController()
          (targetController as? OnWardChosenListener)?.onWardChosen(ward)
        }
      }
    )
    viewModel.viewItemLiveData.observe(this, Observer(::observeViewItem))

    if (savedViewState == null) {
      viewModel.loadWards(stateRegion!!, township!!)
    }
  }

  private fun observeViewItem(viewState: AsyncViewState<List<String>>) {
    when (viewState) {
      is AsyncViewState.Success -> {
        wardRecyclerViewAdapter.submitList(ArrayList(viewState.value))
      }
      is AsyncViewState.Error -> {
        val error = viewState.errorMessage
        binding.tvErrorMessage.text = error
      }
    }
  }

}