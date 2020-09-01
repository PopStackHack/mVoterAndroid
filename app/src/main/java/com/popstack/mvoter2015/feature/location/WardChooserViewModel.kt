package com.popstack.mvoter2015.feature.location

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.popstack.mvoter2015.domain.location.usecase.GetWardsForTownship
import com.popstack.mvoter2015.exception.GlobalExceptionHandler
import com.popstack.mvoter2015.helper.asyncviewstate.AsyncViewStateLiveData
import com.popstack.mvoter2015.helper.livedata.SingleLiveEvent
import kotlinx.coroutines.launch
import javax.inject.Inject

class WardChooserViewModel @Inject constructor(
  private val getWardList: GetWardsForTownship,
  private val globalExceptionHandler: GlobalExceptionHandler
) : ViewModel() {

  val viewItemLiveData = AsyncViewStateLiveData<List<String>>()

  // First one stage/region, Second one ward
  val onWardChosenEvent = SingleLiveEvent<String>()

  val onWardRetryClicked: () -> Unit = {

  }

  val onWardClicked: (String) -> Unit = {
    onWardChosenEvent.postValue(it)
  }

  fun loadWards(stateRegion: String, township: String) {
    viewModelScope.launch {
      viewItemLiveData.postLoading()
      kotlin.runCatching {
        val wardList = getWardList.execute(GetWardsForTownship.Params(stateRegion, township))
        viewItemLiveData.postSuccess(wardList)
      }.exceptionOrNull()?.let { exception ->
        viewItemLiveData.postError(exception, globalExceptionHandler.getMessageForUser(exception))
      }
    }
  }

}