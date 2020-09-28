package com.popstack.mvoter2015.domain.location.usecase

import com.popstack.mvoter2015.domain.CoroutineUseCase
import com.popstack.mvoter2015.domain.DispatcherProvider
import com.popstack.mvoter2015.domain.location.LocationRepository
import javax.inject.Inject

class GetWardsForTownship @Inject constructor(
  private val locationRepository: LocationRepository,
  dispatcherProvider: DispatcherProvider
) : CoroutineUseCase<GetWardsForTownship.Params, List<String>>(dispatcherProvider) {

  // TODO: Change params according to API changes
  data class Params(
    val stateRegion: String,
    val township: String
  )

  override suspend fun provide(input: Params): List<String> {
    return locationRepository.getWardsForTownship(input.stateRegion, input.township)
  }
}