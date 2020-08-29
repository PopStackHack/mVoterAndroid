package com.popstack.mvoter2015.feature.party.search

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.popstack.mvoter2015.data.android.party.PartyPagerFactory
import com.popstack.mvoter2015.domain.party.model.Party
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class PartySearchViewModel @ViewModelInject constructor(
  private val partyPagerFactory: PartyPagerFactory
) : ViewModel() {

  companion object {
    private const val PAGE_SIZE = 20
    private const val DEBOUNCE_TIME_IN_MILLISECONDS = 500L
  }

  var currentQueryValue: String? = null
    private set

  private var currentSearchResult: Flow<PagingData<PartySearchResultViewItem>>? = null

  fun search(query: String): Flow<PagingData<PartySearchResultViewItem>> {
    val lastResult = currentSearchResult
    if (query == currentQueryValue && lastResult != null) {
      return lastResult
    }
    currentQueryValue = query
    val newResult: Flow<PagingData<PartySearchResultViewItem>> = partyPagerFactory.createPager(PAGE_SIZE, query)
      .flow
      .map<PagingData<Party>, PagingData<PartySearchResultViewItem>> { pagingData ->
        pagingData.map<Party, PartySearchResultViewItem> { party ->
          PartySearchResultViewItem(
            partyId = party.id,
            flagImageUrl = party.flagImage,
            name = party.nameBurmese,
            region = party.region
          )
        }
      }
      .cachedIn(viewModelScope)
    currentSearchResult = newResult
    return newResult
  }

}