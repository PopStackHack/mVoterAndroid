package com.popstack.mvoter2015.feature.party.search

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.popstack.mvoter2015.domain.party.usecase.SearchParty
import com.popstack.mvoter2015.helper.livedata.SingleLiveEvent
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class PartySearchViewModel @ViewModelInject constructor(
  private val searchParty: SearchParty,
  private val partySearchPagingSource: PartySearchPagingSource
) : ViewModel() {

  companion object {
    private const val PAGE_SIZE = 20
    private const val DEBOUNCE_TIME_IN_MILLISECONDS = 500L
  }

  val searchResultPagingFlow = Pager(
    PagingConfig(
      pageSize = PartySearchViewModel.PAGE_SIZE
    )
  ) {
    partySearchPagingSource
  }.flow.map { pagingData ->
    pagingData.map { party ->
      PartySearchResultViewItem(
        partyId = party.id,
        flagImageUrl = party.flagImage,
        name = party.nameBurmese,
        region = party.region
      )
    }
  }.cachedIn(viewModelScope)

  val queryEmptyLiveData = SingleLiveEvent<Boolean>()

  val pagingAdapterRefreshSingleLiveEvent = SingleLiveEvent<Unit>()

  var queryTextChangeJob: Job? = null

  init {
    queryEmptyLiveData.postValue(true)
  }

  fun handleSearchQueryTextChange(query: String) {
    queryTextChangeJob?.cancel()
    queryTextChangeJob = viewModelScope.launch {
      delay(DEBOUNCE_TIME_IN_MILLISECONDS)
      performSearch(query)
    }
  }

  private fun performSearch(query: String) {
    queryEmptyLiveData.postValue(query.isEmpty())
    if (query.isNotEmpty()) {
      partySearchPagingSource.query = query
      pagingAdapterRefreshSingleLiveEvent.postValue(Unit)
    }
  }

}