package com.popstack.mvoter2015.feature.analytics.location

import android.content.Context
import com.popstack.mvoter2015.data.cache.BuildConfig
import dagger.Module
import dagger.Provides

@Module
abstract class SelectedLocationAnalyticsModule {

  companion object {

    @Provides
    fun selectedLocationAnalytics(context: Context): SelectedLocationAnalytics {
      return if (BuildConfig.DEBUG) {
        FakeLoggingSelectedLocationAnalyticsImpl()
      } else {
        RealSelectedLocationAnalyticsImpl(context)
      }
    }
  }

}