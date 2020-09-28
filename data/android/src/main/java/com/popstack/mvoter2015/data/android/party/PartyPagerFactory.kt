package com.popstack.mvoter2015.data.android.party

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.popstack.mvoter2015.data.common.party.PartyCacheSource
import com.popstack.mvoter2015.data.common.party.PartyNetworkSource
import com.popstack.mvoter2015.domain.party.model.Party
import com.popstack.mvoter2015.domain.utils.InputSanitizer
import javax.inject.Inject

class PartyPagerFactory @OptIn(ExperimentalPagingApi::class)
@Inject constructor(
  private val partyNetworkSource: PartyNetworkSource,
  private val partyCacheSource: PartyCacheSource
) {

  fun createPager(itemPerPage: Int, query: String? = null): Pager<Int, Party> {
    return Pager(
      config = PagingConfig(
        pageSize = itemPerPage
      ),
      pagingSourceFactory = {
        if (query == null) {
          PartyPagingSource(
            partyCacheSource,
            partyNetworkSource
          )
        } else {
          PartySearchPagingSource(
            partyNetworkSource,
            InputSanitizer.sanitizeInput(query)
          )
        }
      }
    )
  }

}