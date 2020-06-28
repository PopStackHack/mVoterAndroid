package com.popstack.mvoter2015.feature.location

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.popstack.mvoter2015.data.android.location.LocationProvider
import com.popstack.mvoter2015.helper.livedata.SingleLiveEvent
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import timber.log.Timber

class LocationUpdateViewModel @ViewModelInject constructor(
  private val locationProvider: LocationProvider
) : ViewModel() {

  sealed class ViewEvent {

    object ShowLocationRequesting : ViewEvent()

  }

  val viewEventLiveData = SingleLiveEvent<ViewEvent>()

  fun requestLocation() {
    viewModelScope.launch {
      viewEventLiveData.postValue(ViewEvent.ShowLocationRequesting)
      locationProvider.getLocationUpdate().collectLatest { latLng ->
        Timber.d("Location : $latLng")
      }
    }
  }

  fun handleConsentCheckChange(checked: Boolean) {
    //TODO: Update Consent in preference
  }

}