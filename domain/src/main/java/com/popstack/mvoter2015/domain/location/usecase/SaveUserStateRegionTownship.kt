package com.popstack.mvoter2015.domain.location.usecase

import com.popstack.mvoter2015.domain.CoroutineUseCase
import com.popstack.mvoter2015.domain.DispatcherProvider
import com.popstack.mvoter2015.domain.location.LocationRepository
import com.popstack.mvoter2015.domain.location.model.StateRegionTownship
import javax.inject.Inject

class SaveUserStateRegionTownship @Inject constructor(
  private val locationRepository: LocationRepository,
  dispatcherProvider: DispatcherProvider
) : CoroutineUseCase<SaveUserStateRegionTownship.Params, Unit>(dispatcherProvider) {

  // TODO: Change params according to API changes
  data class Params(
    val stateRegionTownship: StateRegionTownship
  )

  override suspend fun provide(input: Params) {
    locationRepository.saveUserStateRegionTownship(
      input.stateRegionTownship
    )
  }
}