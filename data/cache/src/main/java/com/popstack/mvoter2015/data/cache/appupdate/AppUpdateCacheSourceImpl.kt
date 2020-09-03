package com.popstack.mvoter2015.data.cache.appupdate

import android.content.Context
import androidx.core.content.edit
import androidx.preference.PreferenceManager
import com.popstack.mvoter2015.data.common.appupdate.AppUpdate
import com.popstack.mvoter2015.data.common.appupdate.AppUpdateCacheSource
import com.squareup.moshi.Moshi
import javax.inject.Inject

class AppUpdateCacheSourceImpl @Inject constructor(private val context: Context) :
  AppUpdateCacheSource {

  companion object {
    private const val ARG_APP_UPDATE = "app_update"
  }

  private val appUpdatePreference = PreferenceManager.getDefaultSharedPreferences(context)
  private val moshi = Moshi.Builder().build()
  private val cacheEntityJsonAdapter = moshi.adapter(AppUpdateCacheEntity::class.java)

  override fun getLatestUpdate(): AppUpdate? {
    val json = appUpdatePreference.getString(ARG_APP_UPDATE, null) ?: return null
    return cacheEntityJsonAdapter.fromJson(json)?.toAppUpdate()
  }

  override fun putLatestUpdate(appUpdate: AppUpdate) {
    appUpdatePreference.edit {
      putString(ARG_APP_UPDATE, cacheEntityJsonAdapter.toJson(appUpdate.toCacheEntity()))
    }
  }

}