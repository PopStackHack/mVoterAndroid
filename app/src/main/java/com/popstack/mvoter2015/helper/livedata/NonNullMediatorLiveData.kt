package com.popstack.mvoter2015.helper.livedata

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.Observer

/**
 * Using null safely
 * https://proandroiddev.com/nonnull-livedata-with-kotlin-extension-26963ffd0333
 */
class NonNullMediatorLiveData<T> : MediatorLiveData<T>()

fun <T> LiveData<T>.nonNull(): NonNullMediatorLiveData<T> {
  val mediator: NonNullMediatorLiveData<T> =
    NonNullMediatorLiveData()
  mediator.addSource(this, Observer { it?.let { mediator.value = it } })
  return mediator
}

fun <T> NonNullMediatorLiveData<T>.observe(owner: LifecycleOwner, observer: (t: T) -> Unit) {
  this.observe(owner, Observer {
    it?.let(observer)
  })
}