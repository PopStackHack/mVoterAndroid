package com.popstack.mvoter2015.config

import android.content.Context
import androidx.core.content.edit
import androidx.preference.PreferenceManager
import javax.inject.Inject

class AppFirstTimeConfig @Inject constructor(context: Context) {

  private val sharedPref = PreferenceManager.getDefaultSharedPreferences(context)

  companion object {
    private const val KEY_IS_FIRST_TIME = "is_first_time"
  }

  fun isFirstTime(): Boolean {
    return sharedPref.getBoolean(KEY_IS_FIRST_TIME, true)
  }

  fun setFirstTimeStatus(isFirstTime: Boolean) {
    sharedPref.edit {
      putBoolean(KEY_IS_FIRST_TIME, isFirstTime)
    }
  }

}