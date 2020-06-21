package com.popstack.mvoter2015.feature.party.search

import androidx.paging.PagingSource
import com.popstack.mvoter2015.domain.party.model.Party
import com.popstack.mvoter2015.domain.party.usecase.SearchParty
import com.popstack.mvoter2015.exception.GlobalExceptionHandler
import com.popstack.mvoter2015.exception.PagingExceptionWrapper
import timber.log.Timber
import javax.inject.Inject

class PartySearchPagingSource @Inject constructor(
  private val searchParty: SearchParty,
  private val globalExceptionHandler: GlobalExceptionHandler
) : PagingSource<Int, Party>() {

  var query: String = ""

  override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Party> {
    try {
      // Start refresh at page 1 if undefined.
      val pageNumber = params.key ?: 1

      val partyList = if (query.isEmpty()) {
        emptyList()
      } else {
        searchParty.execute(
          SearchParty.Params(
            query = query,
            page = pageNumber,
            itemPerPage = params.loadSize
          )
        )

      }
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
      Timber.e(e)
      // Handle errors in this block and return LoadResult.Error if it is an
      // expected error (such as a network failure).
      return LoadResult.Error(
        PagingExceptionWrapper(
          errorMessage = globalExceptionHandler.getMessageForUser(e),
          originalException = e
        )
      )
    }
  }
}