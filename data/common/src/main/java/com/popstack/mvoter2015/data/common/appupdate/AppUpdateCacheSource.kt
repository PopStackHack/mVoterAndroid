package com.popstack.mvoter2015.data.common.appupdate

interface AppUpdateCacheSource {

  fun getLatestUpdate(): AppUpdate?

  fun putLatestUpdate(appUpdate: AppUpdate)

}