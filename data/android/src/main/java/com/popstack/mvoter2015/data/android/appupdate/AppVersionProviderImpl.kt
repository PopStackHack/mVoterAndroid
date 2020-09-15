package com.popstack.mvoter2015.data.android.appupdate

import com.popstack.mvoter2015.data.android.BuildConfig
import com.popstack.mvoter2015.domain.infra.AppVersionProvider
import javax.inject.Inject

class AppVersionProviderImpl @Inject constructor() : AppVersionProvider {

  override fun versionCode(): Long {
    return BuildConfig.VERSION_CODE.toLong()
  }

  override fun versionName(): String {
    return BuildConfig.VERSION_NAME
  }

}