package com.popstack.mvoter2015.data.android.party

import androidx.paging.PagingSource
import com.popstack.mvoter2015.data.common.party.PartyCacheSource
import com.popstack.mvoter2015.data.common.party.PartyNetworkSource
import com.popstack.mvoter2015.domain.party.model.Party
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber

class PartyPagingSource constructor(
  private val partyCacheSource: PartyCacheSource,
  private val partyNetworkSource: PartyNetworkSource
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
        try {
          val partyListFromNetwork = partyNetworkSource.getPartyList(page, itemPerPage)
          partyCacheSource.putParty(partyListFromNetwork)
          partyCacheSource.getPartyList(page, itemPerPage)
        } catch (exception: Exception) {
          //Network error, see if can recover from cache
          val partyListFromCache = partyCacheSource.getPartyList(page, itemPerPage)
          if (partyListFromCache.isEmpty()) {
            //Seems data is empty, can't recover, throw error
            throw exception
          }
          partyListFromCache
        }
      }

      val nextKey: Int? = if (partyList.isEmpty()) {
        Timber.e("end of pagination")
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