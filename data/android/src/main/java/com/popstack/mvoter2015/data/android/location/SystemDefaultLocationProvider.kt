package com.popstack.mvoter2015.data.android.location

import android.annotation.SuppressLint
import android.content.Context
import android.location.LocationListener
import android.location.LocationManager
import com.popstack.mvoter2015.domain.location.model.LatLng
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import javax.inject.Inject

/**
 * A location provider that can execute without Google Play Service
 * This approach is a fallback that should be used when play service is not available
 */
class SystemDefaultLocationProvider @Inject constructor(
  private val context: Context
) : LocationProvider {

  companion object {
    const val LOCATION_REQUEST_INTERVAL_IN_MILISECONDS = 1000L

    const val MINIMUM_DISTANCE_BETWEEN_INTERVAL_IN_METRE = 1.0f
  }

  private val locationManager =
    context.getSystemService(Context.LOCATION_SERVICE) as LocationManager

  @SuppressLint("MissingPermission")
  override suspend fun getLastKnownLocation(): LatLng? {
    val location =
      locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER) ?: return null
    return LatLng(location.latitude, location.longitude)
  }

  @SuppressLint("MissingPermission")
  override fun getLocationUpdate(): Flow<LatLng> {
    return channelFlow {
      val locationListener =
        LocationListener { location -> offer(LatLng(location.latitude, location.longitude)) }

      locationManager.requestLocationUpdates(
        LocationManager.NETWORK_PROVIDER,
        LOCATION_REQUEST_INTERVAL_IN_MILISECONDS,
        MINIMUM_DISTANCE_BETWEEN_INTERVAL_IN_METRE,
        locationListener
      )

      awaitClose {
        locationManager.removeUpdates(locationListener)
      }
    }
  }

}