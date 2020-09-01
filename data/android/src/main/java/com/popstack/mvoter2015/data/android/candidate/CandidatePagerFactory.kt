package com.popstack.mvoter2015.data.android.candidate

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.popstack.mvoter2015.data.common.candidate.CandidateNetworkSource
import com.popstack.mvoter2015.domain.candidate.model.Candidate
import javax.inject.Inject

class CandidatePagerFactory @OptIn(ExperimentalPagingApi::class)
@Inject constructor(
  private val candidateNetworkSource: CandidateNetworkSource
) {

  fun createSearchPager(itemPerPage: Int, query: String): Pager<Int, Candidate> {
    return Pager(
      config = PagingConfig(
        pageSize = itemPerPage
      ),
      pagingSourceFactory = {
        CandidateSearchPagingSource(
          candidateNetworkSource,
          query
        )
      }
    )
  }

}