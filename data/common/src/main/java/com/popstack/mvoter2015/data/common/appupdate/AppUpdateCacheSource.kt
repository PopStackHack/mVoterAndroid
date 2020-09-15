package com.popstack.mvoter2015.data.common.appupdate

interface AppUpdateCacheSource {

  suspend fun getLatestUpdate(): AppUpdate?

  suspend fun putLatestUpdate(appUpdate: AppUpdate)

}