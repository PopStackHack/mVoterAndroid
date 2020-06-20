package com.popstack.mvoter2015.helper.asyncviewstate

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import timber.log.Timber
import java.util.concurrent.atomic.AtomicBoolean

/**
 * A lifecycle-aware observable that sends Error state only once but revert back to normal LiveData
 * for Loading and Success. This is to prevent Error from being shown again to user on config changes
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
    super.observe(owner, Observer<AsyncViewState<T>> { data ->
      if (data is AsyncViewState.Error) {
        if (pendingError.compareAndSet(true, false)) {
          observer.onChanged(data)
        }
      } else {
        observer.onChanged(data)
      }
    })
  }

  override fun setValue(value: AsyncViewState<T>?) {
    if (value is AsyncViewState.Error) {
      pendingError.set(true)
    }
    super.setValue(value)
  }

  override fun postValue(value: AsyncViewState<T>?) {
    if (value is AsyncViewState.Error) {
      pendingError.set(true)
    }
    super.postValue(value)
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