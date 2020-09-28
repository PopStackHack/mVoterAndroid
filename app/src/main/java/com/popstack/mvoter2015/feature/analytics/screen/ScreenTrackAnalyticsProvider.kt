package com.popstack.mvoter2015.feature.analytics.screen

import android.content.Context

/**
 * Manual injection cuz I don't want to use dagger inside [BaseController]
 * Should prob write accessor to app component but whatever, this works and requires minimal effort
 */
object ScreenTrackAnalyticsProvider {

  fun screenTackAnalytics(context: Context): ScreenTrackAnalytics {
    return ScreenTrackAnalyticsImpl(context)
  }

}