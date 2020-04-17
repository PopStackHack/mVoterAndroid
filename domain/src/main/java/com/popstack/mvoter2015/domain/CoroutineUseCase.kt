package com.popstack.mvoter2015.domain

import kotlinx.coroutines.withContext

abstract class CoroutineUseCase<Input, Output> constructor(
  protected val dispatcherProvider: DispatcherProvider
) {

  suspend fun execute(input: Input): Output {
    return withContext(dispatcherProvider.io()) {
      provide(input)
    }
  }

  protected abstract fun provide(input: Input): Output
}