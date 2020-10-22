package com.popstack.mvoter2015.data.android.appupdate

import android.content.Context
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import com.popstack.mvoter2015.domain.infra.AppVersionProvider
import javax.inject.Inject

class AppVersionProviderImpl @Inject constructor(
  private val context: Context
) : AppVersionProvider {

  override fun versionCode(): Long {
    try {
      val pInfo: PackageInfo = context.packageManager.getPackageInfo(context.packageName, 0)
      return if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.P) {
        pInfo.longVersionCode
      } else {
        pInfo.versionCode.toLong()
      }
    } catch (e: PackageManager.NameNotFoundException) {
      e.printStackTrace()
      return 0L
    }
  }

  override fun versionName(): String {
    try {
      val pInfo: PackageInfo = context.packageManager.getPackageInfo(context.packageName, 0)
      return pInfo.versionName
    } catch (e: PackageManager.NameNotFoundException) {
      e.printStackTrace()
      return ""
    }
  }

}