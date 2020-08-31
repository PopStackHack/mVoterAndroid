package com.popstack.mvoter2015.feature.candidate.listing.upperhouse

import javax.inject.Inject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.popstack.mvoter2015.domain.candidate.model.CandidateId

class UpperHouseCandidateListViewModel @Inject constructor() :
  ViewModel() {

  private val viewItemLiveData = MutableLiveData<List<UpperHouseCandidateListViewItem>>()

  fun loadData() {
    viewItemLiveData.postValue(
      listOf(
        UpperHouseCandidateListViewItem(
          candidateId = CandidateId("aa"),
          name = "Shitsu",
          candidateImage = "",
          candidatePartyFlagImage = "",
          candidatePartyName = "Shiberain"
        )
      )
    )
  }

}