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
  val onStateRegionChosen = SingleLiveEvent<Int>()

  val data = Data()

  inner class Data {
    var chosenStateRegion: String? = null
    var chosenTownship: String? = null
    var viewItems: ArrayList<StateRegionTownshipViewItem> = ArrayList()
    var selectedPosition: Int = -1
  }

  val onStateRegionClicked: (Int, String) -> Unit = onStateRegionClicked@{ position, clickedStateRegion ->
    if (data.chosenStateRegion == clickedStateRegion) {
      toggleSelected(clickedStateRegion)
      viewItemLiveData.postSuccess(data.viewItems)
      return@onStateRegionClicked
    }

    data.chosenStateRegion?.let {
      changeSelected(it, false)
    }

    loadTownships(clickedStateRegion, position)
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

  private fun toggleSelected(stateRegion: String): Boolean {
    data.viewItems.forEachIndexed { index, viewItem ->
      if (viewItem.name == stateRegion) {
        val isSelected = !data.viewItems[index].isSelected
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

  private fun loadTownships(clickedStateRegion: String, position: Int) {
    data.chosenStateRegion = clickedStateRegion
    data.selectedPosition = position

    val shouldLoadTownship = changeSelected(clickedStateRegion, true)

    viewModelScope.launch {
      if (shouldLoadTownship) {
        val params = GetTownshipsForStateRegion.Params(clickedStateRegion)
        kotlin.runCatching {
          val townships = getTownshipList.execute(params)
          setTownship(params.stateRegionIdentifier, townships)
          postSuccessValues()
        }.exceptionOrNull()?.let { exception ->
          val errorMessage = globalExceptionHandler.getMessageForUser(exception)
          setErrorMessage(params.stateRegionIdentifier, errorMessage)
          postSuccessValues()
        }
      } else {
        postSuccessValues()
      }
    }
  }

  private fun postSuccessValues() = with(data) {
    viewItemLiveData.postSuccess(viewItems)
    onStateRegionChosen.postValue(selectedPosition)
  }

  val onTownshipRetryClicked: () -> Unit = onTownshipRetryClicked@{
    loadTownships(data.chosenStateRegion ?: return@onTownshipRetryClicked, data.selectedPosition)
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