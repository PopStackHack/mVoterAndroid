package com.popstack.mvoter2015.domain.location.usecase

import com.popstack.mvoter2015.domain.CoroutineUseCase
import com.popstack.mvoter2015.domain.DispatcherProvider
import com.popstack.mvoter2015.domain.location.LocationRepository
import com.popstack.mvoter2015.domain.location.model.StateRegionTownship
import javax.inject.Inject

class GetUserStateRegionTownship @Inject constructor(
  private val locationRepository: LocationRepository,
  dispatcherProvider: DispatcherProvider
) : CoroutineUseCase<Unit, StateRegionTownship?>(dispatcherProvider) {

  override suspend fun provide(input: Unit): StateRegionTownship? {
    return locationRepository.getUserStateRegionTownship()
  }
}