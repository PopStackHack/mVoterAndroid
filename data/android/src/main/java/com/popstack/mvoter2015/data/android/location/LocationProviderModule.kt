package com.popstack.mvoter2015.data.android.location

import android.content.Context
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GoogleApiAvailability
import dagger.Module
import dagger.Provides

@Module
abstract class LocationProviderModule {

  companion object {

    @Provides
    fun locationProvider(context: Context): LocationProvider {
      return if (GoogleApiAvailability.getInstance()
          .isGooglePlayServicesAvailable(context) == ConnectionResult.SUCCESS
      ) {
        FusedLocationProvider(context)
      } else {
        SystemDefaultLocationProvider(context)
      }
    }
  }

}