package com.popstack.mvoter2015.domain.location.usecase

import com.popstack.mvoter2015.domain.CoroutineUseCase
import com.popstack.mvoter2015.domain.DispatcherProvider
import com.popstack.mvoter2015.domain.location.model.StateRegion
import com.popstack.mvoter2015.domain.location.model.StateRegionPCode
import com.popstack.mvoter2015.domain.location.model.StateRegionType
import javax.inject.Inject

class GetUserStateRegion @Inject constructor(dispatcherProvider: DispatcherProvider) :
  CoroutineUseCase<Unit, StateRegion>(dispatcherProvider) {

  override fun provide(input: Unit): StateRegion {
    return StateRegion(
      pCode = StateRegionPCode("wtf"),
      name = "Hello",
      type = StateRegionType.STATE
    )
  }


}