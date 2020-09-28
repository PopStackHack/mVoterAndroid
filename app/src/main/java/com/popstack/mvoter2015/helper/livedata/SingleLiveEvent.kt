package com.popstack.mvoter2015.helper.livedata

import androidx.annotation.MainThread
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import timber.log.Timber
import java.util.concurrent.atomic.AtomicBoolean

/**
 * A lifecycle-aware observable that sends only new updates after subscription, used for events like
 * navigation and Snackbar messages.
 *
 *
 * This avoids a common problem with events: on configuration change (like rotation) an update
 * can be emitted if the observer is active. This LiveData only calls the observable if there's an
 * explicit call to setValue() or call().
 *
 *
 * Note that only one observer is going to be notified of changes.
 */
class SingleLiveEvent<T> : MutableLiveData<T>() {
  private val pending =
    AtomicBoolean(false)

  @MainThread
  override fun observe(owner: LifecycleOwner, observer: Observer<in T>) {
    if (hasActiveObservers()) {
      Timber.w(
        "Multiple observers registered but only one will be notified of changes."
      )
    }
    super.observe(
      owner,
      Observer<T> { t ->
        if (pending.compareAndSet(true, false)) {
          observer.onChanged(t)
        }
      }
    )
  }

  @MainThread
  override fun setValue(t: T) {
    pending.set(true)
    super.setValue(t)
  }

  companion object {
    private const val TAG = "SingleLiveEvent"
  }
}