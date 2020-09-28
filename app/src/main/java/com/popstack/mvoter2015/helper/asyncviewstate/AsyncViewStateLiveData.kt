package com.popstack.mvoter2015.helper.asyncviewstate

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import timber.log.Timber
import java.util.concurrent.atomic.AtomicBoolean

/**
 * A lifecycle-aware observable that wraps [AsyncViewState]
 */
class AsyncViewStateLiveData<T> : LiveData<AsyncViewState<T>>() {
  private val pendingError =
    AtomicBoolean(false)

  override fun observe(owner: LifecycleOwner, observer: Observer<in AsyncViewState<T>>) {
    if (hasActiveObservers()) {
      Timber.w(
        "Multiple observers registered but only one will be notified of changes."
      )
    }
    super.observe(owner, observer)
  }

  fun postLoading() {
    this.postValue(AsyncViewState.Loading())
  }

  fun postSuccess(data: T) {
    this.postValue(AsyncViewState.Success(data))
  }

  fun postError(exception: Throwable, error: String) {
    this.postValue(AsyncViewState.Error(exception, error))
  }

  companion object {
    private const val TAG = "SingleLiveEvent"
  }
}