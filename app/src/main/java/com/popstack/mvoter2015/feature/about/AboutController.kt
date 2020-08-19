package com.popstack.mvoter2015.feature.about

import android.os.Bundle
import android.view.LayoutInflater
import com.popstack.mvoter2015.core.BaseController
import com.popstack.mvoter2015.databinding.ControllerAboutBinding
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

    binding.viewTermOfUse.setOnClickListener {
      //TODO:
    }

    binding.ivContactFacebook.setOnClickListener {
      //TODO:
    }

    binding.ivContactMail.setOnClickListener {
      //TODO:
    }

    binding.ivContactWebsite.setOnClickListener {
      //TODO:
    }
  }

}