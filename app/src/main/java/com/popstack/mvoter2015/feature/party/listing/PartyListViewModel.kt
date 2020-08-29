package com.popstack.mvoter2015.feature.party.listing

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.popstack.mvoter2015.data.android.party.PartyPagerFactory
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class PartyListViewModel @ViewModelInject constructor(
  private val partyPagerFactory: PartyPagerFactory
) : ViewModel() {

  companion object {
    private const val ITEM_PER_PAGE = 10
  }

  val partyPagingFlow: Flow<PagingData<PartyListViewItem>> =
    partyPagerFactory.createPager(ITEM_PER_PAGE).flow.map { pagingData ->
      pagingData.map { party ->
        PartyListViewItem(
          partyId = party.id,
          flagImageUrl = party.flagImage,
          name = party.nameBurmese,
          region = party.region
        )
      }
    }
      .cachedIn(viewModelScope)

}