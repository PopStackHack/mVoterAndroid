package com.popstack.mvoter2015.feature.party.listing

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import kotlinx.coroutines.flow.map

class PartyListViewModel @ViewModelInject constructor(
  private val partyPagingSource: PartyPagingSource
) : ViewModel() {

  val partyPagingFlow = Pager(
    PagingConfig(pageSize = 10)
  ) {
    partyPagingSource
  }.flow.map { pagingData ->
    pagingData.map { party ->
      PartyListViewItem(
        partyId = party.id,
        flagImageUrl = party.flagUrl,
        name = party.burmeseName,
        region = party.region
      )
    }
  }.cachedIn(viewModelScope)

}