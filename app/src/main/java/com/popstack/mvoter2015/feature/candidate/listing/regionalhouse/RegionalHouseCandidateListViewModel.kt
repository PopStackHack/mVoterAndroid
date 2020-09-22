package com.popstack.mvoter2015.feature.candidate.listing.regionalhouse

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.popstack.mvoter2015.domain.candidate.model.Candidate
import com.popstack.mvoter2015.domain.candidate.usecase.GetMyStateRegionHouseCandidateList
import com.popstack.mvoter2015.domain.candidate.usecase.exception.NoStateRegionConstituencyException
import com.popstack.mvoter2015.domain.constituency.usecase.GetMyStateRegionConstituency
import com.popstack.mvoter2015.domain.location.usecase.GetUserStateRegionTownship
import com.popstack.mvoter2015.exception.GlobalExceptionHandler
import com.popstack.mvoter2015.feature.candidate.listing.CandidateListResult
import com.popstack.mvoter2015.feature.candidate.listing.CandidateSectionTitleViewItem
import com.popstack.mvoter2015.feature.candidate.listing.CandidateViewItem
import com.popstack.mvoter2015.feature.candidate.listing.toSmallCandidateViewItem
import com.popstack.mvoter2015.helper.LocalityUtils
import com.popstack.mvoter2015.helper.asyncviewstate.AsyncViewStateLiveData
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject
import kotlin.collections.set

class RegionalHouseCandidateListViewModel @Inject constructor(
  private val getMyStateRegionHouseCandidateList: GetMyStateRegionHouseCandidateList,
  private val getMyStateRegionConstituency: GetMyStateRegionConstituency,
  private val getUserStateRegionTownship: GetUserStateRegionTownship,
  private val globalExceptionHandler: GlobalExceptionHandler
) : ViewModel() {

  val viewItemLiveData = AsyncViewStateLiveData<CandidateListResult>()

  fun loadCandidates() {
    viewModelScope.launch {
      viewItemLiveData.postLoading()
      try {
        val constituency = getMyStateRegionConstituency.execute(Unit)
          ?: if (LocalityUtils.isTownshipFromNPT(getUserStateRegionTownship.execute(Unit)?.township.orEmpty())) {
            throw NoStateRegionConstituencyException()
          } else return@launch
        if (constituency.remark != null) {
          viewItemLiveData.postSuccess(CandidateListResult.Remark(constituency.remark!!))
          return@launch
        }

        val candidateList = getMyStateRegionHouseCandidateList.execute(Unit).sortedBy { it.sortingBallotOrder }

        val stateRegionCandidateViewItemList = ArrayList<CandidateViewItem>()
        val ethnicCandidatesMap = HashMap<String, ArrayList<Candidate>>()

        // Group by representing constituency for ethnic candidates
        for (candidate in candidateList) {
          candidate.takeIf {
            it.isEthnicCandidate
          }
            ?.run {
              if (ethnicCandidatesMap.containsKey(this.constituency.name)) {
                ethnicCandidatesMap[this.constituency.name]!!.add(candidate)
              } else {
                ethnicCandidatesMap[this.constituency.name] = arrayListOf(candidate)
              }
            } ?: stateRegionCandidateViewItemList.add(candidate.toSmallCandidateViewItem())
        }

        // Sort by representing constituency
        val sortedEthnicConstituency = ethnicCandidatesMap.keys.toTypedArray().sortedArray()

        sortedEthnicConstituency.forEach {
          stateRegionCandidateViewItemList.add(CandidateSectionTitleViewItem(it))
          stateRegionCandidateViewItemList.addAll(
            ethnicCandidatesMap[it]!!
              .sortedBy {
                it.sortingBallotOrder
              }
              .map { candidate -> candidate.toSmallCandidateViewItem() }
          )
        }

        stateRegionCandidateViewItemList.add(0, CandidateSectionTitleViewItem(constituency.name))
        val candidateListViewItem = CandidateListResult.CandidateListViewItem(stateRegionCandidateViewItemList)
        viewItemLiveData.postSuccess(candidateListViewItem)
      } catch (noStateRegionConstituencyException: NoStateRegionConstituencyException) {
        viewItemLiveData.postError(noStateRegionConstituencyException, "")
      } catch (exception: Exception) {
        Timber.e(exception)
        viewItemLiveData.postError(exception, globalExceptionHandler.getMessageForUser(exception))
      }
    }
  }

}