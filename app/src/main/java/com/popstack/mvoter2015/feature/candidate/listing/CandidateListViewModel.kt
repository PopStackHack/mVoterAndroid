package com.popstack.mvoter2015.feature.candidate.listing

import androidx.lifecycle.viewModelScope
import com.popstack.mvoter2015.core.mvp.BaseViewModel
import com.popstack.mvoter2015.domain.house.HouseType
import com.popstack.mvoter2015.domain.location.usecase.GetUserStateRegion
import com.popstack.mvoter2015.feature.party.listing.PartyListView
import kotlinx.coroutines.launch
import javax.inject.Inject

internal class CandidateListViewModel @Inject constructor(
  private val houseViewItemMapper: CandidateListHouseViewItemMapper,
  private val getUserStateRegion: GetUserStateRegion
) : BaseViewModel<CandidateListView>() {

  fun loadHouses() {
    viewModelScope.launch {
      val houseTypes = HouseType.values()
      val stateRegion = getUserStateRegion.execute(Unit)

      val viewItems = houseTypes.map { houseType ->
        houseViewItemMapper.mapFromHouseType(houseType, stateRegion.type)
      }

      view?.setUpHouse(viewItems)
    }
  }

}