package com.popstack.mvoter2015.domain.location.usecase

import com.popstack.mvoter2015.domain.CoroutineUseCase
import com.popstack.mvoter2015.domain.DispatcherProvider
import com.popstack.mvoter2015.domain.location.LocationRepository
import com.popstack.mvoter2015.domain.location.model.StateRegion
import javax.inject.Inject

class GetStateRegionList @Inject constructor(
  private val locationRepository: LocationRepository,
  dispatcherProvider: DispatcherProvider
) : CoroutineUseCase<Unit, List<StateRegion>>(dispatcherProvider) {

  override fun provide(input: Unit): List<StateRegion> {
    return locationRepository.getStateRegionList()
  }
}