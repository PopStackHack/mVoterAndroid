package com.popstack.mvoter2015.feature.party.listing

import androidx.lifecycle.LiveData
import com.popstack.mvoter2015.core.mvp.Viewable

interface PartyListView : Viewable {

  fun subscribeToViewItemLiveData(viewItemLiveData: LiveData<List<PartyListViewItem>>)
}