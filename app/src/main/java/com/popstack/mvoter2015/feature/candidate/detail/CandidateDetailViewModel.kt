package com.popstack.mvoter2015.feature.candidate.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.popstack.mvoter2015.domain.candidate.model.CandidateId
import com.popstack.mvoter2015.domain.candidate.usecase.GetCandidate
import com.popstack.mvoter2015.domain.candidate.usecase.GetRivalCandidateList
import com.popstack.mvoter2015.exception.GlobalExceptionHandler
import com.popstack.mvoter2015.feature.candidate.listing.toSmallCandidateViewItem
import com.popstack.mvoter2015.helper.asyncviewstate.AsyncViewStateLiveData
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

class CandidateDetailViewModel @Inject constructor(
  private val getCandidate: GetCandidate,
  private val getRivalCandidateList: GetRivalCandidateList,
  private val globalExceptionHandler: GlobalExceptionHandler
) : ViewModel() {

  val viewItemLiveData = AsyncViewStateLiveData<CandidateDetailsViewItem>()

  fun loadCandidate(candidateId: CandidateId) {
    viewItemLiveData.postLoading()
    viewModelScope.launch {
      kotlin.runCatching {
        val candidate = getCandidate.execute(GetCandidate.Params(candidateId))
        val candidateInfoViewItem = candidate.toCandidateInfoViewItem()

        val candidatesInSameConstituency = getRivalCandidateList.execute(GetRivalCandidateList.Params(candidate.constituency.id))
        val rivalCandidates = candidatesInSameConstituency.filter { rivalCandidate ->
          rivalCandidate.id != candidate.id
        }.map {
          it.toSmallCandidateViewItem()
        }

        val candidateDetailsViewItem = CandidateDetailsViewItem(candidateInfoViewItem, rivalCandidates)
        viewItemLiveData.postSuccess(candidateDetailsViewItem)
      }.exceptionOrNull()?.let { exception ->
        Timber.e(exception)
        viewItemLiveData.postError(exception, globalExceptionHandler.getMessageForUser(exception))
      }
    }
  }

}