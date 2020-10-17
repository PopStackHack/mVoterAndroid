package com.popstack.mvoter2015.feature.analytics.location

import com.popstack.mvoter2015.domain.location.model.CombinedLocation
import timber.log.Timber
import javax.inject.Inject

class FakeLoggingSelectedLocationAnalyticsImpl @Inject constructor() : SelectedLocationAnalytics {

  override fun logLocation(combinedLocation: CombinedLocation) {
    Timber.i("state: ${combinedLocation.stateRegion}, township : ${combinedLocation.township},  ward : ${combinedLocation.ward ?: "N/A"} ")
  }

}