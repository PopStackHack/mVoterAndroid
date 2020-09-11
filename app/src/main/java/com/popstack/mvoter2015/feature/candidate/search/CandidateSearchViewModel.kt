package com.popstack.mvoter2015.feature.candidate.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.popstack.mvoter2015.data.android.candidate.CandidatePagerFactory
import com.popstack.mvoter2015.feature.candidate.listing.SmallCandidateViewItem
import com.popstack.mvoter2015.feature.candidate.listing.toSmallCandidateViewItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class CandidateSearchViewModel @Inject constructor(
  private val candidatePagerFactory: CandidatePagerFactory
) : ViewModel() {

  companion object {
    private const val PAGE_SIZE = 20
    private const val DEBOUNCE_TIME_IN_MILLISECONDS = 500L
  }

  var currentQueryValue: String? = null
    private set

  private var currentSearchResult: Flow<PagingData<SmallCandidateViewItem>>? = null

  fun search(query: String?): Flow<PagingData<SmallCandidateViewItem>> {
    val lastResult = currentSearchResult
    if (query == currentQueryValue && lastResult != null) {
      return lastResult
    }
    currentQueryValue = query
    val newResult: Flow<PagingData<SmallCandidateViewItem>> = candidatePagerFactory.createSearchPager(
      PAGE_SIZE,
      query.orEmpty()
    ).flow
      .map { pagingData ->
        pagingData.map { candidate ->
          candidate.toSmallCandidateViewItem()
        }
      }
      .cachedIn(viewModelScope)
    currentSearchResult = newResult
    return newResult
  }

}