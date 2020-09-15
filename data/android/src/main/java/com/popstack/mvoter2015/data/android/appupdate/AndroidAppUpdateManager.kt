package com.popstack.mvoter2015.data.android.appupdate

import android.content.Context
import androidx.annotation.VisibleForTesting
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GoogleApiAvailability
import com.popstack.mvoter2015.data.common.appupdate.AppUpdate
import com.popstack.mvoter2015.data.common.appupdate.AppUpdateCacheSource
import com.popstack.mvoter2015.data.common.appupdate.AppUpdateNetworkSource
import com.popstack.mvoter2015.data.network.exception.NetworkException
import com.popstack.mvoter2015.domain.DispatcherProvider
import com.popstack.mvoter2015.domain.infra.AppUpdateManager
import com.popstack.mvoter2015.domain.infra.AppVersionProvider
import kotlinx.coroutines.withContext
import timber.log.Timber
import javax.inject.Inject

class AndroidAppUpdateManager @Inject constructor(
  private val context: Context,
  private val appUpdateNetworkSource: AppUpdateNetworkSource,
  private val appUpdateCacheSource: AppUpdateCacheSource,
  private val appVersionProvider: AppVersionProvider,
  private val dispatcherProvider: DispatcherProvider
) : AppUpdateManager {

  override suspend fun checkForUpdate(): AppUpdateManager.UpdateResult {
    return withContext(dispatcherProvider.io()) {
      val latestUpdate =
        getLatestAppUpdate() ?: return@withContext AppUpdateManager.UpdateResult.NotRequired
      processLatestAppUpdate(latestUpdate)
    }
  }

  @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
  internal suspend fun getLatestAppUpdate(): AppUpdate? {
    try {
      appUpdateNetworkSource.getLatestUpdate(appVersionProvider.versionCode())
        .also { appUpdate ->
          appUpdateCacheSource.putLatestUpdate(appUpdate)
        }
    } catch (networkException: NetworkException) {
      Timber.e(networkException)
    }

    return appUpdateCacheSource.getLatestUpdate()
  }

  @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
  internal fun processLatestAppUpdate(appUpdate: AppUpdate): AppUpdateManager.UpdateResult {
    if (appUpdate.latestVersionCode > appVersionProvider.versionCode()) {

      if (appUpdate.requireForcedUpdate) {
        return AppUpdateManager.UpdateResult.ForcedUpdate(getDownloadLink(appUpdate))
      } else {
        return AppUpdateManager.UpdateResult.RelaxedUpdate(getDownloadLink(appUpdate))
      }
    }

    return AppUpdateManager.UpdateResult.NotRequired
  }

  @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
  internal fun getDownloadLink(appUpdate: AppUpdate): String {
    return if (
      GoogleApiAvailability.getInstance()
        .isGooglePlayServicesAvailable(context) == ConnectionResult.SUCCESS
    ) {
      appUpdate.playStoreLink
    } else {
      appUpdate.selfHostedLink
    }
  }

}