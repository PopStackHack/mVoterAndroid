package com.popstack.mvoter2015.feature.candidate.listing.upperhouse

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.popstack.mvoter2015.domain.candidate.model.CandidateId

class UpperHouseCandidateListViewModel @ViewModelInject constructor() :
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