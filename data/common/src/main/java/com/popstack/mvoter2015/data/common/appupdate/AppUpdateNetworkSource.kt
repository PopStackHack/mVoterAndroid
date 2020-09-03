package com.popstack.mvoter2015.data.common.appupdate

interface AppUpdateNetworkSource {

  fun getLatestUpdate(): AppUpdate

}