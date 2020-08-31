package com.popstack.mvoter2015.feature.location

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.popstack.mvoter2015.data.android.location.LocationProvider
import com.popstack.mvoter2015.domain.location.model.Ward
import com.popstack.mvoter2015.domain.location.usecase.GetWardDetails
import com.popstack.mvoter2015.domain.location.usecase.SaveUserWard
import com.popstack.mvoter2015.helper.livedata.SingleLiveEvent
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

class LocationUpdateViewModel @Inject constructor(
  private val locationProvider: LocationProvider,
  private val getWardDetails: GetWardDetails,
  private val saveUserWard: SaveUserWard
) : ViewModel() {

  sealed class ViewEvent {
    object ShowLocationRequesting : ViewEvent()
    object EnableDoneButton : ViewEvent()
    object NavigateToHomePage : ViewEvent()
  }

  inner class Data {
    var chosenTownship: String? = null
    var chosenStateRegion: String? = null
    var chosenWard: String? = null
    var wardDetails: Ward? = null
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
    data.chosenWard = null
    data.wardDetails = null
  }

  fun onWardChosen(ward: String) {
    data.chosenWard = ward
    viewModelScope.launch {
      kotlin.runCatching {
        data.wardDetails = getWardDetails.execute(GetWardDetails.Params(
          stateRegion = data.chosenStateRegion!!,
          township = data.chosenTownship!!,
          ward = data.chosenWard!!
        ))
        viewEventLiveData.postValue(ViewEvent.EnableDoneButton)
      }.exceptionOrNull()?.let { exception ->
        Timber.e(exception)
      }
    }
  }

  fun onDoneClicked() {
    viewModelScope.launch {
      kotlin.runCatching {
        data.wardDetails?.let {
          saveUserWard.execute(SaveUserWard.Params(it))
        }
        viewEventLiveData.postValue(ViewEvent.NavigateToHomePage)
      }.exceptionOrNull()?.let { exception ->
        Timber.e(exception)
      }
    }
  }

  fun handleConsentCheckChange(checked: Boolean) {
    //TODO: Update Consent in preference
  }

}