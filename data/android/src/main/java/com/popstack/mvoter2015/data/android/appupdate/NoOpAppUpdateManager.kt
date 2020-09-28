package com.popstack.mvoter2015.data.android.appupdate

import com.popstack.mvoter2015.domain.infra.AppUpdateManager
import javax.inject.Inject

/**
 * An App Update Manager that always returns NotRequired
 * To be used in debug builds
 */
class NoOpAppUpdateManager @Inject constructor() : AppUpdateManager {

  override suspend fun checkForUpdate(): AppUpdateManager.UpdateResult {
    return AppUpdateManager.UpdateResult.NotRequired
  }

  override suspend fun skipCurrentUpdate() {
    //DO NOTHING
  }

}