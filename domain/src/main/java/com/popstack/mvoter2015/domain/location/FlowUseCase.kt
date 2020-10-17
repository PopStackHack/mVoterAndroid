package com.popstack.mvoter2015.domain.location

import com.popstack.mvoter2015.domain.DispatcherProvider
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn

abstract class FlowUseCase<I, O> constructor(
  protected val dispatcherProvider: DispatcherProvider
) {

  fun execute(params: I): Flow<O> {
    return provide(params)
      .flowOn(dispatcherProvider.io())
  }

  protected abstract fun provide(
    params: I
  ): Flow<O>

}