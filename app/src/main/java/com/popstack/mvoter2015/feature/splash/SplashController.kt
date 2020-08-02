package com.popstack.mvoter2015.feature.splash

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bluelinelabs.conductor.Controller
import com.bluelinelabs.conductor.RouterTransaction
import com.popstack.mvoter2015.config.AppFirstTimeConfig
import com.popstack.mvoter2015.feature.home.HomeController
import com.popstack.mvoter2015.feature.location.LocationUpdateController
import com.popstack.mvoter2015.helper.conductor.requireContext

class SplashController : Controller() {

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup,
    savedViewState: Bundle?
  ): View {
    val firstTimeConfig = AppFirstTimeConfig(requireContext())
    if (firstTimeConfig.isFirstTime()) {
      router.pushController(RouterTransaction.with(LocationUpdateController()))
    } else {
      router.pushController(RouterTransaction.with(HomeController()))
    }
    return View(requireContext())
  }

}