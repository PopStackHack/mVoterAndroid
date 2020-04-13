package com.popstack.mvoter2015.domain.location.usecase

import com.popstack.mvoter2015.domain.CoroutineUseCase
import com.popstack.mvoter2015.domain.DispatcherProvider
import com.popstack.mvoter2015.domain.location.model.StateRegion
import com.popstack.mvoter2015.domain.location.model.Township
import com.popstack.mvoter2015.domain.location.model.Ward
import javax.inject.Inject

class GetUserWard @Inject constructor(dispatcherProvider: DispatcherProvider) :
  CoroutineUseCase<Unit, Ward>(dispatcherProvider) {

  override fun provide(input: Unit): Ward {
    TODO("Not yet implemented")
  }


}