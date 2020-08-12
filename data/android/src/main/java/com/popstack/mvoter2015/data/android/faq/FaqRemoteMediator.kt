package com.popstack.mvoter2015.data.android.faq

import android.content.Context
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.LoadType.APPEND
import androidx.paging.LoadType.PREPEND
import androidx.paging.LoadType.REFRESH
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.popstack.mvoter2015.data.android.paging.RemoteKeyDbProvider
import com.popstack.mvoter2015.data.cache.entity.FaqRemoteKeyTable
import com.popstack.mvoter2015.data.common.faq.FaqCacheSource
import com.popstack.mvoter2015.data.common.faq.FaqNetworkSource
import com.popstack.mvoter2015.data.network.exception.NetworkException
import com.popstack.mvoter2015.domain.faq.model.Faq
import com.popstack.mvoter2015.domain.faq.model.FaqCategory

@OptIn(ExperimentalPagingApi::class)
class FaqRemoteMediator constructor(
  private val context: Context,
  private val faqNetworkSource: FaqNetworkSource,
  private val faqCacheSource: FaqCacheSource,
  private val category: FaqCategory? = null,
  private val query: String? = null
) : RemoteMediator<Int, Faq>() {

  companion object {
    private const val START_PAGE_KEY = 1
  }

  private val remoteKeyDb = RemoteKeyDbProvider.INSTANCE(context)

  override suspend fun load(
    loadType: LoadType,
    state: PagingState<Int, Faq>
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
      val faqListFromNetwork = faqNetworkSource.getFaqList(page, state.config.pageSize, category = category, query = query)

      val endOfPaginationReached = faqListFromNetwork.isEmpty()

      val prevKey = if (page == START_PAGE_KEY) null else page - 1
      val nextKey = if (endOfPaginationReached) null else page + 1

      remoteKeyDb.transaction {
        if (loadType == LoadType.REFRESH) {
          remoteKeyDb.faqRemoteKeyTableQueries.deleteAll()
        }

        faqListFromNetwork.forEach { faq ->
          remoteKeyDb.faqRemoteKeyTableQueries.insertOrReplace(
            faqId = faq.id,
            previousKey = prevKey?.toLong(),
            nextKey = nextKey?.toLong()
          )
        }
        faqCacheSource.putFaqList(faqListFromNetwork)
      }

      return MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
    } catch (exception: NetworkException) {
      return MediatorResult.Error(exception)
    }
  }

  private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, Faq>): FaqRemoteKeyTable? {
    return state.pages.lastOrNull() { it.data.isNotEmpty() }?.data?.lastOrNull()
      ?.let { faq ->
        // Get the remote keys of the last item retrieved
        remoteKeyDb.faqRemoteKeyTableQueries.selectById(faq.id)
          .executeAsOne()
      }
  }

  private suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, Faq>): FaqRemoteKeyTable? {
    // Get the first page that was retrieved, that contained items.
    // From that first page, get the first item
    return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
      ?.let { faq ->
        // Get the remote keys of the first items retrieved
        remoteKeyDb.faqRemoteKeyTableQueries.selectById(faq.id)
          .executeAsOne()
      }
  }

  private suspend fun getRemoteKeyClosestToCurrentPosition(
    state: PagingState<Int, Faq>
  ): FaqRemoteKeyTable? {
    // The paging library is trying to load data after the anchor position
    // Get the item closest to the anchor position
    return state.anchorPosition?.let { position ->
      state.closestItemToPosition(position)
        ?.let { faq ->
          remoteKeyDb.faqRemoteKeyTableQueries.selectById(faq.id)
            .executeAsOne()
        }
    }
  }

}