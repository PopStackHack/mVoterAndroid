package com.popstack.mvoter2015.feature.location

import android.os.Bundle
import android.view.LayoutInflater
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.popstack.mvoter2015.core.mvp.MvvmController
import com.popstack.mvoter2015.databinding.ControllerTownshipChooserBinding
import com.popstack.mvoter2015.feature.HasRouter
import com.popstack.mvoter2015.helper.asyncviewstate.AsyncViewState
import com.popstack.mvoter2015.helper.conductor.requireActivity
import com.popstack.mvoter2015.logging.HasTag
import timber.log.Timber

class TownshipChooserController : MvvmController<ControllerTownshipChooserBinding>(), HasTag {

  override val tag: String = "TownshipChooserController"

  private val viewModel: TownshipChooserViewModel by viewModels()

  override val bindingInflater: (LayoutInflater) -> ControllerTownshipChooserBinding =
    ControllerTownshipChooserBinding::inflate

  private lateinit var stateRegionTownshipAdapter: StateRegionTownshipRecyclerViewAdapter

  override fun onBindView(savedViewState: Bundle?) {
    super.onBindView(savedViewState)

    stateRegionTownshipAdapter = viewModel.run {
      StateRegionTownshipRecyclerViewAdapter(
        onStateRegionClicked,
        onTownshipClicked,
        onTownshipRetryClicked
      )
    }

    binding.rvStatRegionTownship.apply {
      adapter = stateRegionTownshipAdapter
      layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
    }

    binding.ivClose.setOnClickListener {
      if (requireActivity() is HasRouter) {
        (requireActivity() as HasRouter).router()
          .popCurrentController()
      }
    }

    viewModel.onTownshipChosenEvent.observe(
      this,
      {
        if (requireActivity() is HasRouter) {
          (requireActivity() as HasRouter).router()
            .popCurrentController()
          (targetController as? OnTownshipChosenListener)?.onTownshipChosen(it.first, it.second)
        }
      }
    )
    viewModel.onStateRegionChosen.observe(
      this,
      {
        // TODO: Scroll to top
      }
    )
    viewModel.viewItemLiveData.observe(this, Observer(::observeViewItem))

    if (savedViewState == null) {
      viewModel.loadStateRegions()
    }
  }

  private fun observeViewItem(viewState: AsyncViewState<List<StateRegionTownshipViewItem>>) {
    when (viewState) {
      is AsyncViewState.Success -> {
        Timber.e("$tag : Submitting list")
        viewState.value.map {
          if (it.isSelected) Timber.e("$tag : ${it.name} ${it.isSelected}")
        }
        stateRegionTownshipAdapter.submitList(ArrayList(viewState.value))
      }
      is AsyncViewState.Error -> {
        val error = viewState.errorMessage
        binding.tvErrorMessage.text = error
      }
    }
  }

}