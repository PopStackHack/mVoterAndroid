package com.fakestack.mvoter2015.data.network

import android.content.Context
import com.popstack.mvoter2015.data.common.appupdate.AppUpdate
import com.popstack.mvoter2015.data.common.appupdate.AppUpdateNetworkSource
import javax.inject.Inject

class FakeAppUpdateNetworkDataSource @Inject constructor(
  private val context: Context
) : AppUpdateNetworkSource {

  override fun getLatestUpdate(deviceVersionCode: Long): AppUpdate {
    return AppUpdate(
      latestVersionCode = deviceVersionCode,
      requireForcedUpdate = true,
      playStoreLink = "",
      selfHostedLink = ""
    )
  }

}