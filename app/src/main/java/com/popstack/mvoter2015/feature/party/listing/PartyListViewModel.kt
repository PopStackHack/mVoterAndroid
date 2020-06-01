package com.popstack.mvoter2015.feature.party.listing

import androidx.lifecycle.MutableLiveData
import com.popstack.mvoter2015.core.mvp.BaseViewModel
import javax.inject.Inject

class PartyListViewModel @Inject constructor() : BaseViewModel<PartyListView>() {

  private val viewItemLiveData = MutableLiveData<List<PartyListViewItem>>()

  override fun attachView(viewable: PartyListView) {
    super.attachView(viewable)
    view?.subscribeToViewItemLiveData(viewItemLiveData)
  }

}