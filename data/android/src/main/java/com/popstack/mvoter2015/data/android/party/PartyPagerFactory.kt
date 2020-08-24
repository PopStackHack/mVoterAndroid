package com.popstack.mvoter2015.data.android.party

import android.content.Context
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.popstack.mvoter2015.data.common.party.PartyCacheSource
import com.popstack.mvoter2015.data.common.party.PartyNetworkSource
import com.popstack.mvoter2015.domain.party.model.Party
import javax.inject.Inject

class PartyPagerFactory @OptIn(ExperimentalPagingApi::class)
@Inject constructor(
  private val context: Context,
  private val partyNetworkSource: PartyNetworkSource,
  private val partyCacheSource: PartyCacheSource
) {

  fun createPager(itemPerPage: Int, query: String? = null): Pager<Int, Party> {
    return Pager(
      config = PagingConfig(
        pageSize = itemPerPage
      ),
      remoteMediator = if (query != null) {
        PartySearchRemoteMediator(
          context = context,
          partyCacheSource = partyCacheSource,
          partyNetworkSource = partyNetworkSource,
          query = query
        )
      } else {
        PartyRemoteMediator(
          context = context,
          partyCacheSource = partyCacheSource,
          partyNetworkSource = partyNetworkSource
        )
      },
      pagingSourceFactory = {
        if (query != null) {
          partyCacheSource.searchPartyPaging(itemPerPage, query)
        } else {
          partyCacheSource.getPartyPaging(itemPerPage)
        }
      }
    )
  }

}