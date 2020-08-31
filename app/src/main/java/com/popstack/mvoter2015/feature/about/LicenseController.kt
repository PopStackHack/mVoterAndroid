package com.popstack.mvoter2015.feature.about

import android.os.Bundle
import android.view.LayoutInflater
import com.popstack.mvoter2015.R
import com.popstack.mvoter2015.core.BaseController
import com.popstack.mvoter2015.databinding.ControllerLicenseBinding
import com.popstack.mvoter2015.helper.conductor.requireContext
import com.popstack.mvoter2015.helper.conductor.setSupportActionBar
import com.popstack.mvoter2015.helper.conductor.supportActionBar
import java.io.IOException


class LicenseController : BaseController<ControllerLicenseBinding>() {

  override val bindingInflater: (LayoutInflater) -> ControllerLicenseBinding = ControllerLicenseBinding::inflate

  override fun onBindView(savedViewState: Bundle?) {
    super.onBindView(savedViewState)
    setSupportActionBar(binding.toolBar)
    supportActionBar()?.title = requireContext().getString(R.string.navigation_title_license)
    supportActionBar()?.setDisplayHomeAsUpEnabled(true)

    try {
      val inputStream = requireContext().resources.openRawResource(R.raw.open_source_license)
      val buffer = ByteArray(inputStream.available())
      inputStream.buffered().read(buffer)
      inputStream.close()
      binding.webViewLicense.loadData(String(buffer), "text/html", "utf-8")
    } catch (e: IOException) {
      e.printStackTrace()
    }
  }
}