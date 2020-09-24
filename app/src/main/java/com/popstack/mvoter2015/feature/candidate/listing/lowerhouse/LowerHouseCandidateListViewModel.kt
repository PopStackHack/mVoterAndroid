package com.popstack.mvoter2015.feature.candidate.listing.lowerhouse

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.popstack.mvoter2015.domain.candidate.usecase.GetMyLowerHouseCandidateList
import com.popstack.mvoter2015.domain.constituency.usecase.GetMyLowerHouseConstituency
import com.popstack.mvoter2015.exception.GlobalExceptionHandler
import com.popstack.mvoter2015.feature.candidate.listing.CandidateListResult
import com.popstack.mvoter2015.feature.candidate.listing.toSmallCandidateViewItem
import com.popstack.mvoter2015.helper.asyncviewstate.AsyncViewStateLiveData
import com.popstack.mvoter2015.helper.livedata.SingleLiveEvent
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

class LowerHouseCandidateListViewModel @Inject constructor(
  private val getMyLowerHouseCandidateList: GetMyLowerHouseCandidateList,
  private val globalExceptionHandler: GlobalExceptionHandler,
  private val getMyLowerHouseConstituency: GetMyLowerHouseConstituency,
) : ViewModel() {

  inner class Data {
    var constituencyName: String = ""
  }

  sealed class ViewEvent {
    data class ShowConstituencyName(val consituencyName: String) : ViewEvent()
  }

  val viewItemLiveData = AsyncViewStateLiveData<CandidateListResult>()
  val viewEventLiveData = SingleLiveEvent<ViewEvent>()
  val data = Data()

  fun loadCandidates() {
    viewModelScope.launch {
      viewItemLiveData.postLoading()
      kotlin.runCatching {
        val constituency = getMyLowerHouseConstituency.execute(Unit)
        data.constituencyName = constituency.name
        viewEventLiveData.setValue(ViewEvent.ShowConstituencyName(data.constituencyName))
        if (constituency.remark != null) {
          viewItemLiveData.postSuccess(CandidateListResult.Remark(constituency.remark!!))
          return@launch
        }

        val candidateList = getMyLowerHouseCandidateList.execute(Unit)
        val smallCandidateList = candidateList
          .sortedBy {
            it.sortingBallotOrder
          }
          .map {
            it.toSmallCandidateViewItem()
          }

        viewItemLiveData.postSuccess(CandidateListResult.CandidateListViewItem(smallCandidateList))
      }
        .exceptionOrNull()
        ?.let { exception ->
          Timber.e(exception)
          viewItemLiveData.postError(exception, globalExceptionHandler.getMessageForUser(exception))
        }
    }
  }

}