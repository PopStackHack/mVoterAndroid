package com.popstack.mvoter2015.data.android.party

import android.content.Context
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.LoadType.APPEND
import androidx.paging.LoadType.PREPEND
import androidx.paging.LoadType.REFRESH
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.popstack.mvoter2015.data.android.paging.RemoteKeyDbProvider
import com.popstack.mvoter2015.data.cache.entity.PartySearchRemoteKeyTable
import com.popstack.mvoter2015.data.common.party.PartyCacheSource
import com.popstack.mvoter2015.data.common.party.PartyNetworkSource
import com.popstack.mvoter2015.data.network.exception.NetworkException
import com.popstack.mvoter2015.domain.party.model.Party

@OptIn(ExperimentalPagingApi::class)
class PartySearchRemoteMediator constructor(
  context: Context,
  private val partyNetworkSource: PartyNetworkSource,
  private val partyCacheSource: PartyCacheSource,
  private val query: String
) : RemoteMediator<Int, Party>() {

  companion object {
    private const val START_PAGE_KEY = 1
  }

  private val remoteKeyDb = RemoteKeyDbProvider.INSTANCE(context)

  override suspend fun load(
    loadType: LoadType,
    state: PagingState<Int, Party>
  ): MediatorResult {
    val page: Int = when (loadType) {
      REFRESH -> {
        val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
        remoteKeys?.nextKey?.minus(1)
          ?.toInt() ?: START_PAGE_KEY
      }
      PREPEND -> {
        val remoteKeys = getRemoteKeyForFirstItem(state) ?: return MediatorResult.Success(
          endOfPaginationReached = true
        )
        remoteKeys.previousKey?.toInt() ?: return MediatorResult.Success(
          endOfPaginationReached = true
        )
      }
      APPEND -> {
        val remoteKeys = getRemoteKeyForLastItem(state) ?: return MediatorResult.Success(
          endOfPaginationReached = true
        )
        remoteKeys.nextKey?.toInt() ?: return MediatorResult.Success(endOfPaginationReached = true)
      }
    }
    try {
      val partyListFromNetwork = partyNetworkSource.getPartyList(page, state.config.pageSize, query)

      val endOfPaginationReached = partyListFromNetwork.isEmpty()

      val prevKey = if (page == START_PAGE_KEY) null else page - 1
      val nextKey = if (endOfPaginationReached) null else page + 1

      remoteKeyDb.transaction {
        if (loadType == LoadType.REFRESH) {
          remoteKeyDb.partySearchRemoteKeyTableQueries.deleteAll()
          //TODO: Should delete everything from party table as well?
          //partyCacheSource.wipe()
        }
        0
        partyListFromNetwork.forEach { party ->
          remoteKeyDb.partySearchRemoteKeyTableQueries.insertOrReplace(
            partyId = party.id,
            previousKey = prevKey?.toLong(),
            nextKey = nextKey?.toLong()
          )
        }
        partyCacheSource.putParty(partyListFromNetwork)
      }

      return MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
    } catch (exception: NetworkException) {
      return MediatorResult.Error(exception)
    }
  }

  private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, Party>): PartySearchRemoteKeyTable? {
    return state.pages.lastOrNull() { it.data.isNotEmpty() }?.data?.lastOrNull()
      ?.let { party ->
        // Get the remote keys of the last item retrieved
        remoteKeyDb.partySearchRemoteKeyTableQueries.selectById(party.id)
          .executeAsOne()
      }
  }

  private suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, Party>): PartySearchRemoteKeyTable? {
    // Get the first page that was retrieved, that contained items.
    // From that first page, get the first item
    return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
      ?.let { party ->
        // Get the remote keys of the first items retrieved
        remoteKeyDb.partySearchRemoteKeyTableQueries.selectById(party.id)
          .executeAsOne()
      }
  }

  private suspend fun getRemoteKeyClosestToCurrentPosition(
    state: PagingState<Int, Party>
  ): PartySearchRemoteKeyTable? {
    // The paging library is trying to load data after the anchor position
    // Get the item closest to the anchor position
    return state.anchorPosition?.let { position ->
      state.closestItemToPosition(position)
        ?.let { party ->
          remoteKeyDb.partySearchRemoteKeyTableQueries.selectById(party.id)
            .executeAsOne()
        }
    }
  }

}