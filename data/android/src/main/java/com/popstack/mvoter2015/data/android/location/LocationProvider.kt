package com.popstack.mvoter2015.data.android.location

import com.popstack.mvoter2015.domain.location.model.LatLng
import kotlinx.coroutines.flow.Flow

interface LocationProvider {

  suspend fun getLastKnownLocation(): LatLng?

  fun getLocationUpdate(): Flow<LatLng>
}