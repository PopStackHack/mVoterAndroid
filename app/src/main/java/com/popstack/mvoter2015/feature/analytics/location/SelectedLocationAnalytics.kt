package com.popstack.mvoter2015.feature.analytics.location

import com.popstack.mvoter2015.domain.location.model.CombinedLocation

interface SelectedLocationAnalytics {

  fun logLocation(combinedLocation: CombinedLocation)

}