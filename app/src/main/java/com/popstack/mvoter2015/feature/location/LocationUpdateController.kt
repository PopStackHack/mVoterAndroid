package com.popstack.mvoter2015.feature.location

import android.Manifest
import android.view.LayoutInflater
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.bluelinelabs.conductor.RouterTransaction
import com.popstack.mvoter2015.config.AppFirstTimeConfig
import com.popstack.mvoter2015.core.mvp.MvvmController
import com.popstack.mvoter2015.databinding.ControllerLocationBinding
import com.popstack.mvoter2015.feature.home.HomeController
import com.popstack.mvoter2015.helper.conductor.requireActivityAsAppCompatActivity
import com.popstack.mvoter2015.helper.conductor.requireContext
import com.popstack.mvoter2015.helper.conductor.setSupportActionBar
import com.popstack.mvoter2015.helper.conductor.supportActionBar
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class LocationUpdateController : MvvmController<ControllerLocationBinding>() {

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

  override fun onBindView() {
    super.onBindView()

    setSupportActionBar(binding.toolBar)

    binding.checkBoxConsent.isVisible = firstTimeConfig.isFirstTime()
    if (firstTimeConfig.isFirstTime()) {
      binding.checkBoxConsent.setOnCheckedChangeListener { _, isChecked ->
        viewModel.handleConsentCheckChange(isChecked)
      }
    } else {
      supportActionBar()?.setDisplayHomeAsUpEnabled(true)
    }

    lifecycleScope.launch {
      delay(DELAY_PERMISSION_REQUEST_IN_MILLISECONDS)
      requireActivityAsAppCompatActivity().registerForActivityResult(ActivityResultContracts.RequestPermission()) { isAllowed ->
        if (isAllowed) {
          viewModel.requestLocation()
        }
      }.launch(Manifest.permission.ACCESS_FINE_LOCATION)
    }

    binding.buttonDone.setOnClickListener {
      firstTimeConfig.setFirstTimeStatus(false)
      router.setRoot(RouterTransaction.with(HomeController()))
    }

    viewModel.viewEventLiveData.observe(this, Observer(::observeViewEvent))
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
    }
  }

}