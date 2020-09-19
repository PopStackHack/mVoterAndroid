package com.popstack.mvoter2015.feature.candidate.listing.lowerhouse

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.popstack.mvoter2015.domain.candidate.usecase.GetMyLowerHouseCandidateList
import com.popstack.mvoter2015.domain.constituency.usecase.GetMyLowerHouseConstituency
import com.popstack.mvoter2015.exception.GlobalExceptionHandler
import com.popstack.mvoter2015.feature.candidate.listing.CandidateListResult
import com.popstack.mvoter2015.feature.candidate.listing.CandidateSectionTitleViewItem
import com.popstack.mvoter2015.feature.candidate.listing.CandidateViewItem
import com.popstack.mvoter2015.feature.candidate.listing.toSmallCandidateViewItem
import com.popstack.mvoter2015.helper.asyncviewstate.AsyncViewStateLiveData
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

class LowerHouseCandidateListViewModel @Inject constructor(
  private val getMyLowerHouseCandidateList: GetMyLowerHouseCandidateList,
  private val globalExceptionHandler: GlobalExceptionHandler,
  private val getMyLowerHouseConstituency: GetMyLowerHouseConstituency,
) : ViewModel() {

  val viewItemLiveData = AsyncViewStateLiveData<CandidateListResult>()

  fun loadCandidates() {
    viewModelScope.launch {
      viewItemLiveData.postLoading()
      kotlin.runCatching {
        val constituency = getMyLowerHouseConstituency.execute(Unit)
        val headerItem = CandidateSectionTitleViewItem(constituency.name)
        if (constituency.remark != null) {
          viewItemLiveData.postSuccess(CandidateListResult.Remark(constituency.remark!!))
          return@launch
        }

        val viewItemList = mutableListOf<CandidateViewItem>(headerItem)

        val candidateList = getMyLowerHouseCandidateList.execute(Unit)
        val smallCandidateList = candidateList
          .sortedBy {
            it.sortingBallotOrder
          }
          .map {
            it.toSmallCandidateViewItem()
          }
        viewItemList.addAll(smallCandidateList)

        viewItemLiveData.postSuccess(CandidateListResult.CandidateListViewItem(viewItemList))
      }
        .exceptionOrNull()
        ?.let { exception ->
          Timber.e(exception)
          viewItemLiveData.postError(exception, globalExceptionHandler.getMessageForUser(exception))
        }
    }
  }

}