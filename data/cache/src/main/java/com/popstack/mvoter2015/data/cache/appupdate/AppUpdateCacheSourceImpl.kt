package com.popstack.mvoter2015.data.cache.appupdate

import android.content.Context
import androidx.datastore.createDataStore
import com.popstack.mvoter2015.data.common.appupdate.AppUpdate
import com.popstack.mvoter2015.data.common.appupdate.AppUpdateCacheSource
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class AppUpdateCacheSourceImpl @Inject constructor(context: Context) :
  AppUpdateCacheSource {

  companion object {
    private const val ARG_APP_UPDATE = "app_update"
  }

  private val appUpdateDataStore = context.createDataStore("app_update.pb", AppUpdateSerializer)

  override suspend fun getLatestUpdate(): AppUpdate? {
    try {
      return with(appUpdateDataStore.data.first()) {
        AppUpdate(
          latestVersionCode = this.latestVersionCode,
          requireForcedUpdate = this.requireForcedUpdate,
          playStoreLink = this.playStoreLink,
          selfHostedLink = this.downloadLink
        )
      }
    } catch (exception: Exception) {
      return null
    }
  }

  override suspend fun putLatestUpdate(appUpdate: AppUpdate) {
    appUpdateDataStore.updateData { appUpdateProto ->
      appUpdateProto.toBuilder()
        .setLatestVersionCode(appUpdate.latestVersionCode)
        .setRequireForcedUpdate(appUpdate.requireForcedUpdate)
        .setPlayStoreLink(appUpdate.playStoreLink)
        .setDownloadLink(appUpdate.selfHostedLink)
        .build()
    }
  }

}