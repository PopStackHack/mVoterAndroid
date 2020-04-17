package com.popstack.mvoter2015.feature.candidate.listing.upperhouse

import androidx.lifecycle.MutableLiveData
import com.popstack.mvoter2015.core.mvp.BaseViewModel
import javax.inject.Inject

class UpperHouseCandidateListViewModel @Inject constructor() :
  BaseViewModel<UpperHouseCandidateListView>() {

  private val viewItemLiveData = MutableLiveData<List<UpperHouseCandidateListViewItem>>()

  override fun attachView(viewable: UpperHouseCandidateListView) {
    super.attachView(viewable)
    view?.subscribeToViewItemLiveData(viewItemLiveData)
  }

}