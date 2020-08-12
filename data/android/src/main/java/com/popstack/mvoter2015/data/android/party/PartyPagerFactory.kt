package com.popstack.mvoter2015.data.android.party

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.popstack.mvoter2015.data.common.party.PartyCacheSource
import com.popstack.mvoter2015.domain.party.model.Party
import javax.inject.Inject

class PartyPagerFactory @OptIn(ExperimentalPagingApi::class)
@Inject constructor(
  private val partyCacheSource: PartyCacheSource,
  private val partyRemoteMediator: PartyRemoteMediator
) {

  fun partyPager(itemPerPage: Int): Pager<Int, Party> {
    return Pager(
      config = PagingConfig(
        pageSize = itemPerPage
      ),
      remoteMediator = partyRemoteMediator,
      pagingSourceFactory = {
        partyCacheSource.getPartyPaging(itemPerPage)
      }
    )
  }

}