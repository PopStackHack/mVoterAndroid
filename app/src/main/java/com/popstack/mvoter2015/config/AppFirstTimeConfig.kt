package com.popstack.mvoter2015.config

import android.content.Context
import androidx.datastore.preferences.createDataStore
import androidx.datastore.preferences.edit
import androidx.datastore.preferences.preferencesKey
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class AppFirstTimeConfig @Inject constructor(context: Context) {

  private val sharedPreferences = context.createDataStore("mvoter2020_app_first_time")

  companion object {
    private val KEY_IS_FIRST_TIME = preferencesKey<Boolean>("mvoter2020_is_first_time")
  }

  suspend fun isFirstTime(): Boolean {
    return sharedPreferences.data.first()[KEY_IS_FIRST_TIME] ?: true
  }

  suspend fun setFirstTimeStatus(isFirstTime: Boolean) {
    sharedPreferences.edit {
      it[KEY_IS_FIRST_TIME] = isFirstTime
    }
  }

}