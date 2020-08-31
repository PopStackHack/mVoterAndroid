package com.popstack.mvoter2015.feature.location

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.popstack.mvoter2015.domain.location.model.Township
import com.popstack.mvoter2015.domain.location.usecase.GetStateRegionList
import com.popstack.mvoter2015.domain.location.usecase.GetTownshipsForStateRegion
import com.popstack.mvoter2015.exception.GlobalExceptionHandler
import com.popstack.mvoter2015.helper.asyncviewstate.AsyncViewStateLiveData
import com.popstack.mvoter2015.helper.livedata.SingleLiveEvent
import kotlinx.coroutines.launch
import javax.inject.Inject

class TownshipChooserViewModel @Inject constructor(
  private val getStateRegionList: GetStateRegionList,
  private val getTownshipList: GetTownshipsForStateRegion,
  private val globalExceptionHandler: GlobalExceptionHandler
) : ViewModel() {

  val viewItemLiveData = AsyncViewStateLiveData<List<StateRegionTownshipViewItem>>()

  // First one stage/region, Second one township
  val onTownshipChosenEvent = SingleLiveEvent<Pair<String, String>>()

  val data = Data()

  inner class Data {
    var chosenStateRegion: String? = null
    var chosenTownship: String? = null
    var viewItems: ArrayList<StateRegionTownshipViewItem> = ArrayList()
  }

  val onStateRegionClicked: (String) -> Unit = { clickedStateRegion ->
    data.chosenStateRegion?.let {
      changeSelected(it, false)
    }

    data.chosenStateRegion = clickedStateRegion

    val shouldLoadTownship = changeSelected(clickedStateRegion, true)

    if (shouldLoadTownship)
      viewModelScope.launch {
        val params = GetTownshipsForStateRegion.Params(clickedStateRegion)
        kotlin.runCatching {
          val townships = getTownshipList.execute(params)
          setTownship(params.stateRegionIdentifier, townships)
          viewItemLiveData.postSuccess(data.viewItems)
        }.exceptionOrNull()?.let { exception ->
          val errorMessage = globalExceptionHandler.getMessageForUser(exception)
          setErrorMessage(params.stateRegionIdentifier, errorMessage)
          viewItemLiveData.postSuccess(data.viewItems)
        }
      }
    else
      viewItemLiveData.postSuccess(data.viewItems)
  }

  private fun setTownship(stateRegion: String, townships: List<Township>) {
    data.viewItems.forEachIndexed { index, viewItem ->
      if (viewItem.name == stateRegion) {
        data.viewItems[index] = data.viewItems[index].copy(
          isLoading = false,
          error = "",
          townshipList = townships.map { TownshipViewItem(it.pCode.value, it.name) }
        )
        return@forEachIndexed
      }
    }
  }

  private fun setErrorMessage(stateRegion: String, errorMessage: String) {
    data.viewItems.forEachIndexed { index, viewItem ->
      if (viewItem.name == stateRegion) {
        data.viewItems[index] = data.viewItems[index].copy(
          isLoading = false,
          error = errorMessage
        )
        return@forEachIndexed
      }
    }
  }

  private fun changeSelected(stateRegion: String, isSelected: Boolean): Boolean {
    data.viewItems.forEachIndexed { index, viewItem ->
      if (viewItem.name == stateRegion) {
        val shouldLoadTownships = isSelected && data.viewItems[index].townshipList.isEmpty()
        data.viewItems[index] = data.viewItems[index].copy(
          isSelected = isSelected,
          isLoading = shouldLoadTownships
        )
        return shouldLoadTownships
      }
    }
    return false
  }

  val onTownshipRetryClicked: () -> Unit = {

  }

  val onTownshipClicked: (String) -> Unit = {
    this.data.chosenTownship = it
    onTownshipChosenEvent.postValue(Pair(this.data.chosenStateRegion!!, this.data.chosenTownship!!))
  }

  fun loadStateRegions() {
    viewModelScope.launch {
      viewItemLiveData.postLoading()
      kotlin.runCatching {
        val stateRegion = getStateRegionList.execute(Unit).map {
          StateRegionTownshipViewItem(it)
        }
        data.viewItems = ArrayList(stateRegion)
        viewItemLiveData.postSuccess(stateRegion)
      }.exceptionOrNull()?.let { exception ->
        viewItemLiveData.postError(exception, globalExceptionHandler.getMessageForUser(exception))
      }
    }
  }

}