package com.popstack.mvoter2015.data.android.party

import androidx.paging.PagingSource
import com.popstack.mvoter2015.data.common.party.PartyNetworkSource
import com.popstack.mvoter2015.domain.party.model.Party
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber

class PartySearchPagingSource constructor(
  private val partyNetworkSource: PartyNetworkSource,
  private val query: String
) : PagingSource<Int, Party>() {

  companion object {
    private const val STARTING_PAGE = 1
  }

  override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Party> {
    try {
      // Start refresh at page 1 if undefined.
      val page = params.key ?: STARTING_PAGE
      val itemPerPage = params.pageSize

      val partyList = withContext(Dispatchers.IO) {
        partyNetworkSource.getPartyList(
          page = page,
          itemPerPage = itemPerPage,
          query = query
        )
      }

      val nextKey: Int? = if (partyList.isEmpty()) {
        null
      } else {
        page + 1
      }

      return LoadResult.Page(
        data = partyList,
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