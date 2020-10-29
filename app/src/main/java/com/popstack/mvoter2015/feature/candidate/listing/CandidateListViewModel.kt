package com.popstack.mvoter2015.feature.candidate.listing

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.popstack.mvoter2015.domain.constituency.model.HouseType
import com.popstack.mvoter2015.domain.location.usecase.GetUserWard
import com.popstack.mvoter2015.domain.location.usecase.UpdateWardDetails
import com.popstack.mvoter2015.helper.asyncviewstate.AsyncViewStateLiveData
import kotlinx.coroutines.launch
import javax.inject.Inject

class CandidateListViewModel @Inject constructor(
  private val houseViewItemMapper: CandidateListHouseViewItemMapper,
  private val updateWardDetails: UpdateWardDetails,
  private val getUserWard: GetUserWard
) : ViewModel() {

  sealed class HouseViewItemListResult {
    object RequestUserLocation : HouseViewItemListResult()

    data class HouseViewItemList(
      val itemList: List<CandidateListHouseViewItem>
    ) : HouseViewItemListResult()
  }

  val houseViewItemListResultLiveData =
    AsyncViewStateLiveData<HouseViewItemListResult>()

  fun loadHouses() {
    viewModelScope.launch {
      val userWard = getUserWard.execute(Unit) ?: run {
        houseViewItemListResultLiveData.postSuccess(HouseViewItemListResult.RequestUserLocation)
        return@launch
      }

      houseViewItemListResultLiveData.postLoading()

      try {
        updateWardDetails.execute(Unit)
      } catch (exception: Exception) {
        //Ignore exception, allow update to fail
      } finally {
        val houseTypes = HouseType.values()
        val viewItems = houseTypes.map { houseType ->
          houseViewItemMapper.mapFromHouseType(houseType, userWard)
        }
        houseViewItemListResultLiveData.postSuccess(HouseViewItemListResult.HouseViewItemList(viewItems))
      }
    }
  }
}