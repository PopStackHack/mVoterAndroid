package com.popstack.mvoter2015

import android.app.Application
import android.os.Build
import dagger.hilt.android.HiltAndroidApp
import io.sentry.android.core.SentryAndroid
import timber.log.Timber
import timber.log.Timber.DebugTree

@HiltAndroidApp
class MVoterApp : Application() {

  override fun onCreate() {
    super.onCreate()

    SentryAndroid.init(this) { options ->
      options.setBeforeSend { event, _ ->
        event.environment = BuildConfig.BUILD_TYPE

        if (BuildConfig.BUILD_TYPE == "debug") {
          return@setBeforeSend null
        } else {
          return@setBeforeSend event
        }
      }
    }

    if (BuildConfig.DEBUG) {
      Timber.plant(DebugTree())
    }
  }

  private fun isRoboUnitTest(): Boolean {
    return "robolectric" == Build.FINGERPRINT
  }
}