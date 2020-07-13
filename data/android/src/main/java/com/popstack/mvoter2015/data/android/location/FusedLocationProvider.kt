package com.popstack.mvoter2015.data.android.location

import android.annotation.SuppressLint
import android.content.Context
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GoogleApiAvailability
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.tasks.Task
import com.popstack.mvoter2015.domain.location.model.LatLng
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

/**
 * A Location Provider that is dependent on Google Play Service
 */
class FusedLocationProvider @Inject constructor(
  private val context: Context
) : LocationProvider {

  private val locationClient = LocationServices.getFusedLocationProviderClient(context)

  @SuppressLint("MissingPermission")
  override suspend fun getLastKnownLocation(): LatLng? {
    if (GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(context) == ConnectionResult.SUCCESS) {
      val lastLocation = locationClient.lastLocation.await() ?: return null
      return LatLng(lastLocation.latitude, lastLocation.longitude)
    } else {
      return null
    }
  }

  @ExperimentalCoroutinesApi
  @SuppressLint("MissingPermission")
  override fun getLocationUpdate(): Flow<LatLng> {
    return channelFlow {
      val locationCallback = object : LocationCallback() {
        override fun onLocationResult(result: LocationResult) {
          val lastLocation = result.lastLocation
          offer(LatLng(lastLocation.latitude, lastLocation.longitude))
        }
      }
      getLastKnownLocation()?.let { offer(it) }
      val locationRequest = LocationRequest.create()
      locationClient.requestLocationUpdates(locationRequest, locationCallback, null).await()
      awaitClose {
        locationClient.removeLocationUpdates(locationCallback)
      }
    }
  }
}

//Copied from http://suresh-anothernetprogrammer.blogspot.com/2019/06/fcm-coroutine.html
private suspend fun <T> Task<T>.await(): T? = suspendCoroutine { continuation ->
  this.addOnCompleteListener { task ->
    if (task.isSuccessful) {
      continuation.resume(task.result)
    } else {
      continuation.resumeWithException(task.exception!!)
    }
  }
}