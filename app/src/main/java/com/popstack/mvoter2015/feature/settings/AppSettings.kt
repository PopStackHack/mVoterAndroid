package com.popstack.mvoter2015.feature.settings

import android.content.Context
import androidx.datastore.preferences.createDataStore
import androidx.datastore.preferences.edit
import androidx.datastore.preferences.preferencesKey
import com.popstack.mvoter2015.feature.settings.AppTheme.SYSTEM_DEFAULT
import kotlinx.coroutines.flow.first
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppSettings @Inject constructor(context: Context) {

  companion object {
    private val PREF_THEME = preferencesKey<String>("theme")
    private val PREF_EXTERNAL_BROWSER = preferencesKey<Boolean>("use_external_browser")
  }

  private val sharedPreferences = context.createDataStore("app_settings")

  suspend fun getTheme(): AppTheme {
    return AppTheme.valueOf(sharedPreferences.data.first()[PREF_THEME] ?: return SYSTEM_DEFAULT)
  }

  suspend fun updateTheme(appTheme: AppTheme) {
    sharedPreferences.edit {
      it[PREF_THEME] = appTheme.toString()
    }
  }

  suspend fun getUseExternalBrowser(): Boolean {
    return sharedPreferences.data.first()[PREF_EXTERNAL_BROWSER] ?: false
  }

  suspend fun updateUseExternalBrowser(useExternalBrowser: Boolean) {
    sharedPreferences.edit {
      it[PREF_EXTERNAL_BROWSER] = useExternalBrowser
    }
  }

}