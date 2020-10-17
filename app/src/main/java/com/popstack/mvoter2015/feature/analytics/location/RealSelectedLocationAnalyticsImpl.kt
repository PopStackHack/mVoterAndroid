package com.popstack.mvoter2015.feature.analytics.location

import android.content.Context
import com.google.firebase.analytics.FirebaseAnalytics
import com.popstack.mvoter2015.domain.location.model.CombinedLocation
import javax.inject.Inject

class RealSelectedLocationAnalyticsImpl @Inject constructor(
  private val context: Context
) : SelectedLocationAnalytics {

  companion object {
    private const val PROPERTY_NAME_GEO = "geo"
  }

  override fun logLocation(combinedLocation: CombinedLocation) {
    val property = with(combinedLocation) {
      if (ward == null) {
        "$stateRegion|$township"
      } else {
        "$ward|$township|$stateRegion"
      }
    }
    FirebaseAnalytics.getInstance(context).setUserProperty(PROPERTY_NAME_GEO, property)
  }

}