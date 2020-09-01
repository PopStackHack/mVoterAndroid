package com.popstack.mvoter2015.feature.faq.ballot

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.popstack.mvoter2015.domain.faq.model.BallotExampleCategory
import com.popstack.mvoter2015.domain.faq.usecase.GetBallotExampleList
import com.popstack.mvoter2015.exception.GlobalExceptionHandler
import com.popstack.mvoter2015.helper.asyncviewstate.AsyncViewStateLiveData
import kotlinx.coroutines.launch
import javax.inject.Inject

class BallotExampleViewModel @Inject constructor(
  private val getBallotExampleList: GetBallotExampleList,
  private val globalExceptionHandler: GlobalExceptionHandler
) : ViewModel() {

  private var selectedBallotExampleCategory: BallotExampleCategory? = null

  val ballotExampleCategoryLiveData = MutableLiveData<BallotExampleCategory>()

  val ballotViewItemLiveData = AsyncViewStateLiveData<List<BallotExampleViewItem>>()

  var invalidBallotStartPosition = 0
    private set

  var validBallotStartPosition = 0
    private set

  fun selectBallotExampleCategory(ballotExampleCategory: BallotExampleCategory) {
    viewModelScope.launch {

      if (ballotExampleCategory == selectedBallotExampleCategory) {
        return@launch
      }
      selectedBallotExampleCategory = ballotExampleCategory
      ballotExampleCategoryLiveData.postValue(selectedBallotExampleCategory)
      ballotViewItemLiveData.postLoading()
      try {

        val viewItem = getBallotExampleList.execute(selectedBallotExampleCategory!!).map {
          BallotExampleViewItem(
            id = it.id,
            image = it.image,
            isValid = it.isValid,
            reason = it.reason
          )
        }.sortedBy {
          it.isValid.not()
        }

        invalidBallotStartPosition = viewItem.indexOfFirst { it.isValid.not() }
        validBallotStartPosition = viewItem.indexOfFirst { it.isValid }

        ballotViewItemLiveData.postSuccess(viewItem)
      } catch (exception: Exception) {
        ballotViewItemLiveData.postError(
          exception,
          globalExceptionHandler.getMessageForUser(exception)
        )
      }
    }
  }

  fun selectedBallotExampleCategory(): BallotExampleCategory? {
    return selectedBallotExampleCategory
  }
}