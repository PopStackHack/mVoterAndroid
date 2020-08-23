package com.popstack.mvoter2015.feature.about

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import com.popstack.mvoter2015.core.BaseController
import com.popstack.mvoter2015.databinding.ControllerAboutBinding
import com.popstack.mvoter2015.helper.conductor.requireContext
import com.popstack.mvoter2015.helper.conductor.setSupportActionBar
import com.popstack.mvoter2015.helper.conductor.supportActionBar

class AboutController : BaseController<ControllerAboutBinding>() {

  override val bindingInflater: (LayoutInflater) -> ControllerAboutBinding =
    ControllerAboutBinding::inflate

  override fun onBindView(savedViewState: Bundle?) {
    super.onBindView(savedViewState)

    setSupportActionBar(binding.toolBar)
    supportActionBar()?.title = ""
    supportActionBar()?.setDisplayHomeAsUpEnabled(true)

    binding.viewTermOfUse.setOnClickListener {
      //TODO:
    }

    binding.viewPrivacyPolicy.setOnClickListener {
      //TODO:
    }

    binding.viewLicense.setOnClickListener {
      //TODO:
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
  }

  /**
   * Copied from https://stackoverflow.com/a/15022153/3125020
   */
  private fun openEmail() {
    runCatching {
      val emailIntent = Intent(Intent.ACTION_SENDTO, Uri.fromParts(
        "mailto", "popstackhack@gmail.com", null))
      emailIntent.putExtra(Intent.EXTRA_EMAIL, arrayOf("popstackhack@gmail.com")) //To make it work on 4.3 in case we support older version
      startActivity(Intent.createChooser(emailIntent, "Send email..."))
    }
  }

  private fun openAppWebsite() {
    runCatching {
      startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://mvoterapp.com/")))
    }
  }

  private fun openFacebookPage() {
    try {
      requireContext().packageManager.getPackageInfo("com.facebook.katana", 0)
      startActivity(
        Intent(Intent.ACTION_VIEW, Uri.parse("fb://page/1731925863693956")))
    } catch (e: Exception) {
      runCatching {
        startActivity(
          Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/mvoter2015")))
      }
    }

  }

}