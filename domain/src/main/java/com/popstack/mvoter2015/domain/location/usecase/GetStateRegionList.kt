package com.popstack.mvoter2015.domain.location.usecase

import com.popstack.mvoter2015.domain.CoroutineUseCase
import com.popstack.mvoter2015.domain.DispatcherProvider
import com.popstack.mvoter2015.domain.location.LocationRepository
import javax.inject.Inject

class GetStateRegionList @Inject constructor(
  private val locationRepository: LocationRepository,
  dispatcherProvider: DispatcherProvider
) : CoroutineUseCase<Unit, List<String>>(dispatcherProvider) {

  override suspend fun provide(input: Unit): List<String> {
    return locationRepository.getStateRegionList()
  }
}