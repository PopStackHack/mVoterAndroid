package com.popstack.mvoter2015.domain.location.usecase

import com.popstack.mvoter2015.domain.CoroutineUseCase
import com.popstack.mvoter2015.domain.DispatcherProvider
import com.popstack.mvoter2015.domain.location.model.Township
import javax.inject.Inject

class GetUserTownship @Inject constructor(dispatcherProvider: DispatcherProvider) :
  CoroutineUseCase<Unit, Township>(dispatcherProvider) {

  override fun provide(input: Unit): Township {
    TODO("Not yet implemented")
  }
}