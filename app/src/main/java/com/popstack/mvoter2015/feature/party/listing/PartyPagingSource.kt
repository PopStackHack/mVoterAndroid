package com.popstack.mvoter2015.feature.party.listing

import androidx.paging.PagingSource
import com.popstack.mvoter2015.domain.party.model.Party
import com.popstack.mvoter2015.domain.party.usecase.GetPartyList
import javax.inject.Inject

class PartyPagingSource @Inject constructor(
  private val getPartyList: GetPartyList
) : PagingSource<Int, Party>() {

  override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Party> {
    try {
      // Start refresh at page 1 if undefined.
      val pageNumber = params.key ?: 1
      val partyList = getPartyList.execute(
        GetPartyList.Params(
          page = pageNumber,
          itemPerPage = params.loadSize
        )
      )

      val nextKey: Int? = if (partyList.isEmpty()) {
        null
      } else {
        pageNumber + 1
      }

      return LoadResult.Page(
        data = partyList,
        prevKey = null, // Only paging forward.
        nextKey = nextKey
      )
    } catch (e: Exception) {
      // Handle errors in this block and return LoadResult.Error if it is an
      // expected error (such as a network failure).
      return LoadResult.Error(e)
    }
  }
}