package com.popstack.mvoter2015.feature.candidate.listing

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.popstack.mvoter2015.domain.house.HouseType
import com.popstack.mvoter2015.domain.location.usecase.GetUserStateRegion
import kotlinx.coroutines.launch

internal class CandidateListViewModel @ViewModelInject constructor(
  private val houseViewItemMapper: CandidateListHouseViewItemMapper,
  private val getUserStateRegion: GetUserStateRegion
) : ViewModel() {

  fun loadHouses() {
    viewModelScope.launch {
      val houseTypes = HouseType.values()
      val stateRegion = getUserStateRegion.execute(Unit)

      val viewItems = houseTypes.map { houseType ->
        houseViewItemMapper.mapFromHouseType(houseType, stateRegion.type)
      }
    }
  }

}