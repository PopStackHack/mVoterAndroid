package com.popstack.mvoter2015.feature.location

import android.os.Bundle
import android.view.LayoutInflater
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.bluelinelabs.conductor.RouterTransaction
import com.popstack.mvoter2015.R
import com.popstack.mvoter2015.config.AppFirstTimeConfig
import com.popstack.mvoter2015.core.mvp.MvvmController
import com.popstack.mvoter2015.databinding.ControllerLocationBinding
import com.popstack.mvoter2015.feature.HasRouter
import com.popstack.mvoter2015.feature.analytics.screen.CanTrackScreen
import com.popstack.mvoter2015.feature.home.BottomNavigationHostController
import com.popstack.mvoter2015.helper.conductor.requireActivity
import com.popstack.mvoter2015.helper.conductor.requireContext
import com.popstack.mvoter2015.helper.conductor.supportActionBar
import com.popstack.mvoter2015.logging.HasTag
import kotlinx.coroutines.launch

class LocationUpdateController :
  MvvmController<ControllerLocationBinding>(),
  HasTag,
  OnTownshipChosenListener,
  OnWardChosenListener,
  CanTrackScreen {

  override val tag: String = "LocationUpdateController"

  override val screenName: String = "LocationUpdateController"

  private val viewModel: LocationUpdateViewModel by viewModels()

  override val bindingInflater: (LayoutInflater) -> ControllerLocationBinding =
    ControllerLocationBinding::inflate

  private val firstTimeConfig by lazy {
    AppFirstTimeConfig(requireContext())
  }

  override fun onBindView(savedViewState: Bundle?) {
    super.onBindView(savedViewState)

    lifecycleScope.launch {
      supportActionBar()?.setDisplayHomeAsUpEnabled(true)
    }

    binding.buttonDone.icon = ContextCompat.getDrawable(requireContext(), R.drawable.ic_baseline_check_white_24)
    binding.buttonDone.isEnabled = false

    binding.buttonDone.setOnClickListener {
      viewModel.onDoneClicked()
    }

    binding.buttonRegion.setOnClickListener {
      if (requireActivity() is HasRouter) {
        val townshipController = TownshipChooserController()
        townshipController.targetController = this
        (requireActivity() as HasRouter).router()
          .pushController(RouterTransaction.with(townshipController))
      }
    }

    binding.buttonWard.setOnClickListener {
      if (requireActivity() is HasRouter) {
        viewModel.data.chosenTownship?.let {
          val wardChooserController =
            WardChooserController.newInstance(viewModel.data.chosenStateRegion!!, it)
          wardChooserController.targetController = this
          (requireActivity() as HasRouter).router()
            .pushController(RouterTransaction.with(wardChooserController))
        }
      }
    }

    binding.ivClose.setOnClickListener {
      changeFirstTimeStatusAndNavigateToHome()
    }

    setupButtons()

    viewModel.viewEventLiveData.observe(this, Observer(::observeViewEvent))
  }

  private fun changeFirstTimeStatusAndNavigateToHome() {
    lifecycleScope.launch {
      firstTimeConfig.setFirstTimeStatus(false)
    }

    if (router.backstackSize > 1) {
      router.popCurrentController()
    } else {
      router.setRoot(
        RouterTransaction.with(BottomNavigationHostController())
          .tag(BottomNavigationHostController.TAG)
      )
    }
  }

  private fun setupButtons() {
    viewModel.data.chosenTownship?.let {
      binding.buttonRegion.text = it
      binding.buttonWard.isEnabled = true
    }
    viewModel.data.chosenWard?.let {
      binding.buttonWard.text = viewModel.data.chosenWard
    } ?: run {
      binding.buttonWard.setText(R.string.location_chooser_ward)
    }
    binding.buttonDone.isEnabled = viewModel.data.wardDetails != null
    binding.buttonWard.isVisible = !viewModel.data.isTownshipFromNPT
  }

  private fun observeViewEvent(viewEvent: LocationUpdateViewModel.ViewEvent) {
    when (viewEvent) {
      LocationUpdateViewModel.ViewEvent.EnableDoneButton -> {
        binding.buttonDone.icon = ContextCompat.getDrawable(requireContext(), R.drawable.ic_baseline_check_white_24)
        binding.buttonDone.isEnabled = true
        binding.progressBar.isVisible = false
      }
      LocationUpdateViewModel.ViewEvent.ShowConstituencyLoading -> {
        binding.buttonDone.isEnabled = false
        binding.tvErrorMessage.isVisible = false
        binding.buttonDone.icon = null
        binding.progressBar.isVisible = true
      }
      LocationUpdateViewModel.ViewEvent.NavigateToHomePage -> {
        changeFirstTimeStatusAndNavigateToHome()
      }
      LocationUpdateViewModel.ViewEvent.ShowWardField -> {
        binding.buttonWard.isVisible = true
      }
      LocationUpdateViewModel.ViewEvent.HideWardField -> {
        binding.buttonWard.setText(R.string.location_chooser_choose_ward)
        binding.buttonWard.isVisible = false
      }
      is LocationUpdateViewModel.ViewEvent.ShowErrorMessage -> {
        binding.buttonDone.icon = ContextCompat.getDrawable(requireContext(), R.drawable.ic_baseline_check_white_24)
        binding.buttonDone.isEnabled = true
        binding.progressBar.isVisible = false
        binding.tvErrorMessage.isVisible = true
        binding.tvErrorMessage.text = viewEvent.error
      }
    }
  }

  override fun onTownshipChosen(
    stateRegion: String,
    township: String
  ) {
    viewModel.onTownshipChosen(stateRegion, township)
    setupButtons()
  }

  override fun onWardChosen(ward: String) {
    binding.buttonDone.isEnabled = false
    viewModel.onWardChosen(ward)
    setupButtons()
  }

}

interface OnTownshipChosenListener {
  fun onTownshipChosen(
    stateRegion: String,
    township: String
  )
}

interface OnWardChosenListener {
  fun onWardChosen(ward: String)
}