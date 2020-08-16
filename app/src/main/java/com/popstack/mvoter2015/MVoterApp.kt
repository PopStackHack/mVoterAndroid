package com.popstack.mvoter2015

import android.app.Application
import android.os.Build
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.crashlytics.FirebaseCrashlytics
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber
import timber.log.Timber.DebugTree

@HiltAndroidApp
class MVoterApp : Application() {

  override fun onCreate() {
    super.onCreate()

    if (BuildConfig.DEBUG) {
      //Setup Debug Configs
      Timber.plant(DebugTree())
    } else {
      //Setup Non-Debug Configs
      FirebaseCrashlytics.getInstance().setCrashlyticsCollectionEnabled(true)
      FirebaseAnalytics.getInstance(this).setAnalyticsCollectionEnabled(true)
    }
  }

  private fun isRoboUnitTest(): Boolean {
    return "robolectric" == Build.FINGERPRINT
  }
}