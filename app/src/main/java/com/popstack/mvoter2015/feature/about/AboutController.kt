package com.popstack.mvoter2015.feature.about

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.text.LineBreaker
import android.net.Uri
import android.os.Bundle
import android.text.Layout
import android.view.LayoutInflater
import androidx.lifecycle.lifecycleScope
import com.bluelinelabs.conductor.RouterTransaction
import com.popstack.mvoter2015.BuildConfig
import com.popstack.mvoter2015.R
import com.popstack.mvoter2015.core.LifeCycleAwareController
import com.popstack.mvoter2015.databinding.ControllerAboutBinding
import com.popstack.mvoter2015.di.Injectable
import com.popstack.mvoter2015.feature.analytics.screen.CanTrackScreen
import com.popstack.mvoter2015.feature.browser.OpenBrowserDelegate
import com.popstack.mvoter2015.helper.conductor.requireActivity
import com.popstack.mvoter2015.helper.conductor.requireContext
import com.popstack.mvoter2015.helper.conductor.setSupportActionBar
import com.popstack.mvoter2015.helper.conductor.supportActionBar
import com.popstack.mvoter2015.logging.HasTag
import kotlinx.coroutines.launch
import javax.inject.Inject

class AboutController : LifeCycleAwareController<ControllerAboutBinding>(), Injectable, HasTag, CanTrackScreen {

  override val tag: String = "AboutController"

  override val screenName: String = "AboutController"

  @Inject
  lateinit var openBrowserDelegate: OpenBrowserDelegate

  override val bindingInflater: (LayoutInflater) -> ControllerAboutBinding =
    ControllerAboutBinding::inflate

  @SuppressLint("WrongConstant")
  override fun onBindView(savedViewState: Bundle?) {
    super.onBindView(savedViewState)

    setSupportActionBar(binding.toolBar)
    supportActionBar()?.title = ""
    supportActionBar()?.setDisplayHomeAsUpEnabled(true)

    binding.viewTermOfUse.setOnClickListener {
      runCatching {
        lifecycleScope.launch {
          openBrowserDelegate.browserHandler()
            .launchInBrowser(requireActivity(), "https://mvoterapp.com/terms")
        }
      }
    }

    binding.viewPrivacyPolicy.setOnClickListener {
      runCatching {
        lifecycleScope.launch {
          openBrowserDelegate.browserHandler()
            .launchInBrowser(requireActivity(), "https://mvoterapp.com/privacy")
        }
      }
    }

    binding.viewLicense.setOnClickListener {
      router.pushController(RouterTransaction.with(LicenseController()))
    }

    binding.ivContactFacebook.setOnClickListener {
      openFacebookPage()
    }

    binding.ivContactMail.setOnClickListener {
      openEmail()
    }

    binding.ivContactWebsite.setOnClickListener {
      openAppWebsite()
    }

    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.Q) {
      if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.Q) {
        binding.tvCandidatePrivacyInstruction.justificationMode = LineBreaker.JUSTIFICATION_MODE_INTER_WORD
      } else {
        binding.tvCandidatePrivacyInstruction.justificationMode = Layout.JUSTIFICATION_MODE_INTER_WORD
      }
    }

    binding.tvVersion.text = requireContext().getString(R.string.version, BuildConfig.VERSION_NAME)
  }

  /**
   * Copied from https://stackoverflow.com/a/15022153/3125020
   */
  private fun openEmail() {
    runCatching {
      val emailIntent = Intent(
        Intent.ACTION_SENDTO,
        Uri.fromParts("mailto", "popstackhack@gmail.com", null)
      )
      emailIntent.putExtra(
        Intent.EXTRA_EMAIL, arrayOf("popstackhack@gmail.com")
      ) //To make it work on 4.3 in case we support older version
      startActivity(Intent.createChooser(emailIntent, "Send email..."))
    }
  }

  private fun openAppWebsite() {
    runCatching {
      lifecycleScope.launch {
        openBrowserDelegate.browserHandler()
          .launchInBrowser(requireActivity(), "https://mvoterapp.com/")
      }
    }
  }

  private fun openFacebookPage() {
    try {
      requireContext().packageManager.getPackageInfo("com.facebook.katana", 0)
      startActivity(
        Intent(
          Intent.ACTION_VIEW,
          Uri.parse("fb://page/1731925863693956")
        )
      )
    } catch (e: Exception) {
      runCatching {
        lifecycleScope.launch {
          openBrowserDelegate.browserHandler()
            .launchInBrowser(requireActivity(), "https://www.facebook.com/mvoter2015")
        }
      }
    }

  }

}