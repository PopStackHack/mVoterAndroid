package com.popstack.mvoter2015.feature.faq.ballot

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.popstack.mvoter2015.domain.faq.usecase.GetBallotExampleList
import com.popstack.mvoter2015.helper.asyncviewstate.AsyncViewStateLiveData
import kotlinx.coroutines.launch

class BallotExampleViewModel @ViewModelInject constructor(
  private val getBallotExampleList: GetBallotExampleList
) : ViewModel() {

  val ballotViewItemLiveData = AsyncViewStateLiveData<List<BallotExampleViewItem>>()

  fun loadData() {
    viewModelScope.launch {
      ballotViewItemLiveData.postLoading()

      runCatching {
        val viewItem = getBallotExampleList.execute(Unit).map {
          BallotExampleViewItem(
            id = it.id,
            image = it.image,
            isValid = it.isValid,
            reason = it.reason
          )
        }

        ballotViewItemLiveData.postSuccess(viewItem)
      }
    }
  }

}