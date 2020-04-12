package com.popstack.mvoter2015.helper

import com.popstack.mvoter2015.domain.DispatcherProvider
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

class AndroidDispatcherProvider @Inject constructor() : DispatcherProvider {
  override fun main(): CoroutineDispatcher = Dispatchers.Main
  override fun io(): CoroutineDispatcher = Dispatchers.IO
  override fun default(): CoroutineDispatcher = Dispatchers.Default
  override fun unconfined(): CoroutineDispatcher = Dispatchers.Unconfined
}