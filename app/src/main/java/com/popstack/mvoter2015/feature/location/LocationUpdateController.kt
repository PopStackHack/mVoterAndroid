package com.popstack.mvoter2015.feature.location

import android.os.Bundle
import android.view.LayoutInflater
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
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
import com.popstack.mvoter2015.feature.home.BottomNavigationHostController
import com.popstack.mvoter2015.helper.conductor.requireActivity
import com.popstack.mvoter2015.helper.conductor.requireContext
import com.popstack.mvoter2015.helper.conductor.supportActionBar
import com.popstack.mvoter2015.logging.HasTag
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class LocationUpdateController :
  MvvmController<ControllerLocationBinding>(),
  HasTag,
  OnTownshipChosenListener,
  OnWardChosenListener {

  override val tag: String = "LocationUpdateController"

  private val viewModel: LocationUpdateViewModel by viewModels()

  override val bindingInflater: (LayoutInflater) -> ControllerLocationBinding =
    ControllerLocationBinding::inflate

  private val firstTimeConfig by lazy {
    AppFirstTimeConfig(requireContext())
  }

  companion object {
    private const val DELAY_PERMISSION_REQUEST_IN_MILLISECONDS = 500L
    private const val ALPHA_FULL = 1.0f
    private const val ALPHA_NONE = 0.0f
    private const val ANIMATION_DURATION_IN_MILLISECONDS = 1000L
  }

  override fun onBindView(savedViewState: Bundle?) {
    super.onBindView(savedViewState)

    lifecycleScope.launch {
      supportActionBar()?.setDisplayHomeAsUpEnabled(true)
    }

    lifecycleScope.launch {
      delay(DELAY_PERMISSION_REQUEST_IN_MILLISECONDS)
      // TODO : Integrate when the API is ready
//      requireActivityAsAppCompatActivity().registerForActivityResult(ActivityResultContracts.RequestPermission()) { isAllowed ->
//        if (isAllowed) {
//          viewModel.requestLocation()
//        }
//      }.launch(Manifest.permission.ACCESS_FINE_LOCATION)
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
  }

  private fun observeViewEvent(viewEvent: LocationUpdateViewModel.ViewEvent) {
    when (viewEvent) {
      LocationUpdateViewModel.ViewEvent.ShowLocationRequesting -> {
        binding.tvRequestLocation.isVisible = true
        val fadeInFadeOutAnimation = AlphaAnimation(ALPHA_FULL, ALPHA_NONE)
        fadeInFadeOutAnimation.duration = ANIMATION_DURATION_IN_MILLISECONDS
        fadeInFadeOutAnimation.repeatCount = Animation.INFINITE
        fadeInFadeOutAnimation.repeatMode = Animation.REVERSE
        binding.tvRequestLocation.animation = fadeInFadeOutAnimation
      }
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