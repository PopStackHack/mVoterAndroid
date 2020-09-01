package com.popstack.mvoter2015.data.android.candidate

import androidx.paging.PagingSource
import com.popstack.mvoter2015.data.common.candidate.CandidateNetworkSource
import com.popstack.mvoter2015.domain.candidate.model.Candidate
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber

class CandidateSearchPagingSource constructor(
  private val candidateNetworkSource: CandidateNetworkSource,
  private val query: String
) : PagingSource<Int, Candidate>() {

  companion object {
    private const val STARTING_PAGE = 1
  }

  override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Candidate> {
    try {
      // Start refresh at page 1 if undefined.
      val page = params.key ?: STARTING_PAGE

      val candidateList = withContext(Dispatchers.IO) {
        candidateNetworkSource.searchCandidate(
          pageNo = page,
          query = query
        )
      }

      val nextKey: Int? = if (candidateList.isEmpty()) {
        null
      } else {
        page + 1
      }

      return LoadResult.Page(
        data = candidateList,
        prevKey = null, // Only paging forward.
        nextKey = nextKey
      )
    } catch (e: Exception) {
      Timber.e(e)
      // Handle errors in this block and return LoadResult.Error if it is an
      // expected error (such as a network failure).
      return LoadResult.Error(e)
    }
  }

}