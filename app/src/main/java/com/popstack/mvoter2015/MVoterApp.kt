package com.popstack.mvoter2015

import android.app.Application
import android.os.Build
import com.popstack.mvoter2015.di.AppInjector
import com.popstack.mvoter2015.di.component.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import timber.log.Timber
import timber.log.Timber.DebugTree
import javax.inject.Inject

class MVoterApp : Application(), HasAndroidInjector {

  @Inject lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Any>

  override fun androidInjector(): AndroidInjector<Any> {
    return dispatchingAndroidInjector
  }

  override fun onCreate() {
    super.onCreate()

    DaggerAppComponent.builder()
        .application(this)
        .build()
        .inject(this)

    AppInjector.initAutoInjection(this)

    if (BuildConfig.DEBUG) {
      Timber.plant(DebugTree())
    }
  }

  private fun isRoboUnitTest(): Boolean {
    return "robolectric" == Build.FINGERPRINT
  }
}