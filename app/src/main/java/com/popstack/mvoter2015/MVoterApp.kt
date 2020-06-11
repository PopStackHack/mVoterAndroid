package com.popstack.mvoter2015

import android.app.Application
import android.os.Build
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber
import timber.log.Timber.DebugTree

@HiltAndroidApp
class MVoterApp : Application() {

  override fun onCreate() {
    super.onCreate()

    if (BuildConfig.DEBUG) {
      Timber.plant(DebugTree())
    }
  }

  private fun isRoboUnitTest(): Boolean {
    return "robolectric" == Build.FINGERPRINT
  }
}