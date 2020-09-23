package com.popstack.mvoter2015.feature.analytics.screen

import android.content.Context
import android.os.Bundle
import com.google.firebase.analytics.FirebaseAnalytics
import javax.inject.Inject

class ScreenTrackAnalyticsImpl @Inject constructor(
  context: Context
) : ScreenTrackAnalytics {

  private val analytics = FirebaseAnalytics.getInstance(context)

  override fun trackScreen(canTrackScreen: CanTrackScreen) {
    val bundle = Bundle()
    bundle.putString(FirebaseAnalytics.Param.SCREEN_NAME, canTrackScreen.screenName)
    bundle.putString(FirebaseAnalytics.Param.SCREEN_CLASS, canTrackScreen.screenName)
    analytics.logEvent(FirebaseAnalytics.Event.SCREEN_VIEW, bundle)
  }

}