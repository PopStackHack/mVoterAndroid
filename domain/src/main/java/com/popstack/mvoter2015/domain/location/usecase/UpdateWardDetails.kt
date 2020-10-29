package com.popstack.mvoter2015.domain.location.usecase

import com.popstack.mvoter2015.domain.CoroutineUseCase
import com.popstack.mvoter2015.domain.DispatcherProvider
import com.popstack.mvoter2015.domain.location.LocationRepository
import javax.inject.Inject

class UpdateWardDetails @Inject constructor(
  dispatcherProvider: DispatcherProvider,
  private val locationRepository: LocationRepository
) : CoroutineUseCase<Unit, Unit>(dispatcherProvider) {

  override suspend fun provide(input: Unit) {
    val stateRegionTownship = locationRepository.getUserStateRegionTownship() ?: return
    val ward = locationRepository.getUserWard() ?: return

    val updatedWard = locationRepository.getWardDetails(
      stateRegionTownship.stateRegion,
      stateRegionTownship.township,
      ward.name
    )

    locationRepository.saveUserWard(updatedWard)
  }

}