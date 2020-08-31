package com.popstack.mvoter2015.feature.location

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.popstack.mvoter2015.data.android.location.LocationProvider
import com.popstack.mvoter2015.domain.location.usecase.GetUserWard
import com.popstack.mvoter2015.domain.location.usecase.GetWardDetails
import com.popstack.mvoter2015.domain.location.usecase.SaveUserWard
import com.popstack.mvoter2015.helper.livedata.SingleLiveEvent
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import timber.log.Timber

class LocationUpdateViewModel @ViewModelInject constructor(
  private val locationProvider: LocationProvider,
  private val getWardDetails: GetWardDetails,
  private val saveUserWard: SaveUserWard
) : ViewModel() {

  sealed class ViewEvent {
    object ShowLocationRequesting : ViewEvent()
    object EnableDoneButton: ViewEvent()
  }

  inner class Data {
    var chosenTownship: String? = null
    var chosenStateRegion: String? = null
    var chosenWard: String? = null
  }

  val data = Data()
  val viewEventLiveData = SingleLiveEvent<ViewEvent>()

  fun requestLocation() {
    viewModelScope.launch {
      viewEventLiveData.postValue(ViewEvent.ShowLocationRequesting)
      locationProvider.getLocationUpdate().collectLatest { latLng ->
        Timber.d("Location : $latLng")
      }
    }
  }

  fun onTownshipChosen(chosenStateRegion: String, chosenTownship: String) {
    data.chosenStateRegion = chosenStateRegion
    data.chosenTownship = chosenTownship
  }

  fun onWardChosen(ward: String) {
    data.chosenWard = ward
    viewModelScope.launch {
      kotlin.runCatching {
        val wardDetails = getWardDetails.execute(GetWardDetails.Params(
          stateRegion = data.chosenStateRegion!!,
          township = data.chosenTownship!!,
          ward = data.chosenWard!!
        ))
        saveUserWard.execute(SaveUserWard.Params(wardDetails))
        viewEventLiveData.postValue(ViewEvent.EnableDoneButton)
      }.exceptionOrNull()?.let { exception ->
        Timber.e(exception)
      }
    }
  }

  fun handleConsentCheckChange(checked: Boolean) {
    //TODO: Update Consent in preference
  }

}