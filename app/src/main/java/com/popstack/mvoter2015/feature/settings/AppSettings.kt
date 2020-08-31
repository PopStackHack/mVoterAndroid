package com.popstack.mvoter2015.feature.settings

import android.content.Context
import androidx.core.content.edit
import androidx.preference.PreferenceManager
import javax.inject.Inject

class AppSettings @Inject constructor(context: Context) {

  companion object {
    private const val PREF_THEME = "theme"
    private const val PREF_EXTERNAL_BROWSER = "use_external_browser"
  }

  private val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)

  fun getTheme(): AppTheme {
    return AppTheme.valueOf(sharedPreferences.getString(PREF_THEME, null)
      ?: return AppTheme.SYSTEM_DEFAULT)
  }

  fun updateTheme(appTheme: AppTheme) {
    sharedPreferences.edit {
      putString(PREF_THEME, appTheme.toString())
    }
  }

  fun getUseExternalBrowser(): Boolean {
    return sharedPreferences.getBoolean(PREF_EXTERNAL_BROWSER, false)
  }

  fun updateUseExternalBrowser(useExternalBrowser: Boolean) {
    sharedPreferences.edit {
      putBoolean(PREF_EXTERNAL_BROWSER, useExternalBrowser)
    }
  }

}