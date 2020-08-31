package com.popstack.mvoter2015.feature.settings

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatDelegate
import com.popstack.mvoter2015.core.BaseController
import com.popstack.mvoter2015.databinding.ControllerSettingsBinding
import com.popstack.mvoter2015.helper.conductor.requireContext
import com.popstack.mvoter2015.helper.conductor.setSupportActionBar
import com.popstack.mvoter2015.helper.conductor.supportActionBar
import com.popstack.mvoter2015.helper.extensions.safeSelection
import com.popstack.mvoter2015.helper.extensions.setOnItemSelectedListener
import dagger.hilt.EntryPoints

class SettingsController : BaseController<ControllerSettingsBinding>() {

  override val bindingInflater: (LayoutInflater) -> ControllerSettingsBinding = ControllerSettingsBinding::inflate

  private val appSettings by lazy {
    EntryPoints.get(requireContext(), AppSettingsEntryPoint::class.java).appSettings()
  }

  companion object {
    private const val POSITION_THEME_SYSTEM_DEFAULT = 0
    private const val POSITION_THEME_LIGHT = 1
    private const val POSITION_THEME_DARK = 2
  }

  private val appThemeSpinnerAdapter by lazy {
    AppThemeSpinnerAdapter(requireContext())
  }

  override fun onBindView(savedViewState: Bundle?) {
    super.onBindView(savedViewState)

    setSupportActionBar(binding.toolBar)
    supportActionBar()?.setDisplayHomeAsUpEnabled(true)

    binding.spinnerTheme.adapter = appThemeSpinnerAdapter

    when (appSettings.getTheme()) {
      AppTheme.SYSTEM_DEFAULT -> binding.spinnerTheme.safeSelection(POSITION_THEME_SYSTEM_DEFAULT)
      AppTheme.LIGHT -> binding.spinnerTheme.safeSelection(POSITION_THEME_LIGHT)
      AppTheme.DARK -> binding.spinnerTheme.safeSelection(POSITION_THEME_DARK)
    }

    binding.switchOpenExternalBrowser.isChecked = appSettings.getUseExternalBrowser()

    binding.spinnerTheme.setOnItemSelectedListener { parent, view, position, id ->
      val appTheme = when (position) {
        POSITION_THEME_SYSTEM_DEFAULT -> AppTheme.SYSTEM_DEFAULT
        POSITION_THEME_LIGHT -> AppTheme.LIGHT
        POSITION_THEME_DARK -> AppTheme.DARK
        else -> throw IllegalArgumentException("$position is not valid theme array index")
      }
      appSettings.updateTheme(appTheme)
      when (appTheme) {
        AppTheme.SYSTEM_DEFAULT -> {
          AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
        }
        AppTheme.LIGHT -> {
          AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
        AppTheme.DARK -> {
          AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        }
      }
    }

    binding.switchOpenExternalBrowser.setOnCheckedChangeListener { compoundButton, isChecked ->
      appSettings.updateUseExternalBrowser(isChecked)
    }

  }
}