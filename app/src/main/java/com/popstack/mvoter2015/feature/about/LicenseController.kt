package com.popstack.mvoter2015.feature.about

import android.os.Bundle
import android.view.LayoutInflater
import androidx.lifecycle.lifecycleScope
import com.popstack.mvoter2015.R
import com.popstack.mvoter2015.core.LifeCycleAwareController
import com.popstack.mvoter2015.databinding.ControllerLicenseBinding
import com.popstack.mvoter2015.helper.conductor.requireContext
import com.popstack.mvoter2015.helper.conductor.setSupportActionBar
import com.popstack.mvoter2015.helper.conductor.supportActionBar
import java.io.IOException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber

class LicenseController : LifeCycleAwareController<ControllerLicenseBinding>() {

  override val bindingInflater: (LayoutInflater) -> ControllerLicenseBinding = ControllerLicenseBinding::inflate

  override fun onBindView(savedViewState: Bundle?) {
    super.onBindView(savedViewState)
    setSupportActionBar(binding.toolBar)
    supportActionBar()?.title = requireContext().getString(R.string.navigation_title_license)
    supportActionBar()?.setDisplayHomeAsUpEnabled(true)

    lifecycleScope.launch {
      try {
        val buffer: ByteArray
        withContext(Dispatchers.IO) {
          val inputStream = requireContext().resources.openRawResource(R.raw.open_source_license)
          buffer = ByteArray(inputStream.available())
          val bufferedStream = inputStream.buffered()
          bufferedStream.read(buffer)
          bufferedStream.close()
          inputStream.close()
        }
        binding.webViewLicense.loadDataWithBaseURL(null, String(buffer), "text/html", "utf-8", null)
      } catch (e: IOException) {
        Timber.e(e)
      }
    }
  }
}