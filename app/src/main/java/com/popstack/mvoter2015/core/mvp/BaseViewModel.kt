package com.popstack.mvoter2015.core.mvp

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineExceptionHandler

/**
 * Created by Vincent on 12/6/18
 */
abstract class BaseViewModel<viewable : Viewable>() :
  ViewModel(), Presentable<viewable> {

  protected var view: viewable? = null


  override fun attachView(viewable: viewable) {
    this.view = viewable
  }

  override fun detachView() {
    this.view = null
  }

  override fun onCleared() {
    super.onCleared()
  }

}