package com.popstack.mvoter2015.feature.candidate.listing

import javax.inject.Inject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.popstack.mvoter2015.domain.constituency.model.HouseType
import com.popstack.mvoter2015.domain.location.usecase.GetUserStateRegion
import com.popstack.mvoter2015.domain.location.usecase.GetUserWard
import com.popstack.mvoter2015.helper.livedata.SingleLiveEvent
import kotlinx.coroutines.launch

class CandidateListViewModel @Inject constructor(
  private val houseViewItemMapper: CandidateListHouseViewItemMapper,
  private val getUserStateRegion: GetUserStateRegion,
  private val getUserWard: GetUserWard
) : ViewModel() {

  val houseViewItemListLiveData =
    SingleLiveEvent<List<CandidateListHouseViewItem>>()

  fun loadHouses() {
    viewModelScope.launch {
      val houseTypes = HouseType.values()
      val stateRegion = getUserStateRegion.execute(Unit)

      val userWard = getUserWard.execute(Unit) ?: return@launch

      val viewItems = houseTypes.map { houseType ->
        houseViewItemMapper.mapFromHouseType(houseType, stateRegion.type, userWard)
      }
      houseViewItemListLiveData.postValue(viewItems)
    }
  }

}