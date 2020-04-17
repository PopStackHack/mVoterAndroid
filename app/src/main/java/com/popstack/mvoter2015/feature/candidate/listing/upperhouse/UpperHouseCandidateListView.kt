package com.popstack.mvoter2015.feature.candidate.listing.upperhouse

import androidx.lifecycle.LiveData
import com.popstack.mvoter2015.core.mvp.Viewable

interface UpperHouseCandidateListView : Viewable {
  fun subscribeToViewItemLiveData(viewItemLiveData: LiveData<List<UpperHouseCandidateListViewItem>>)
}