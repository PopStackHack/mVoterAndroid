package com.popstack.mvoter2015.feature.candidate.listing.regionalhouse

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.popstack.mvoter2015.domain.candidate.model.Candidate
import com.popstack.mvoter2015.domain.candidate.usecase.GetMyStateRegionHouseCandidateList
import com.popstack.mvoter2015.domain.candidate.usecase.exception.NoStateRegionConstituencyException
import com.popstack.mvoter2015.domain.exception.NetworkException
import com.popstack.mvoter2015.exception.GlobalExceptionHandler
import com.popstack.mvoter2015.feature.candidate.listing.CandidateListViewItem
import com.popstack.mvoter2015.feature.candidate.listing.CandidateViewItem
import com.popstack.mvoter2015.feature.candidate.listing.EthnicConstituencyTitleViewItem
import com.popstack.mvoter2015.feature.candidate.listing.toSmallCandidateViewItem
import com.popstack.mvoter2015.helper.asyncviewstate.AsyncViewStateLiveData
import javax.inject.Inject
import kotlin.collections.set
import kotlinx.coroutines.launch
import timber.log.Timber

class RegionalHouseCandidateListViewModel @Inject constructor(
  private val getMyStateRegionHouseCandidateList: GetMyStateRegionHouseCandidateList,
  private val globalExceptionHandler: GlobalExceptionHandler
) : ViewModel() {

  val viewItemLiveData = AsyncViewStateLiveData<CandidateListViewItem>()

  fun loadCandidates() {
    viewModelScope.launch {
      viewItemLiveData.postLoading()
      try {
        val candidateList = getMyStateRegionHouseCandidateList.execute(Unit).sortedBy { it.sortingBallotOrder }

        val stateRegionCandidateViewItemList = ArrayList<CandidateViewItem>()
        val ethnicCandidatesMap = HashMap<String, ArrayList<Candidate>>()

        // Group by representing constituency for ethnic candidates
        for (candidate in candidateList) {
          candidate.takeIf {
            it.isEthnicCandidate
          }
            ?.run {
              if (ethnicCandidatesMap.containsKey(constituency.name)) {
                ethnicCandidatesMap[constituency.name]!!.add(candidate)
              } else {
                ethnicCandidatesMap[constituency.name] = arrayListOf(candidate)
              }
            } ?: stateRegionCandidateViewItemList.add(candidate.toSmallCandidateViewItem())
        }

        // Sort by representing constituency
        val sortedEthnicConstituency = ethnicCandidatesMap.keys.toTypedArray().sortedArray()

        sortedEthnicConstituency.forEach {
          stateRegionCandidateViewItemList.add(EthnicConstituencyTitleViewItem(it))
          stateRegionCandidateViewItemList.addAll(
            ethnicCandidatesMap[it]!!
              .sortedBy {
                it.sortingBallotOrder
              }
              .map { candidate -> candidate.toSmallCandidateViewItem() }
          )
        }

        val candidateListViewItem = CandidateListViewItem(stateRegionCandidateViewItemList)
        viewItemLiveData.postSuccess(candidateListViewItem)
      } catch (noStateRegionConstituencyException: NoStateRegionConstituencyException) {
        viewItemLiveData.postError(noStateRegionConstituencyException, "")
      } catch (networkException: NetworkException) {
        Timber.e(networkException)
        viewItemLiveData.postError(networkException, globalExceptionHandler.getMessageForUser(networkException))
      }
    }
  }

}