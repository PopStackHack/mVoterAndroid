package com.popstack.mvoter2015.domain.location.usecase

import com.popstack.mvoter2015.domain.CoroutineUseCase
import com.popstack.mvoter2015.domain.DispatcherProvider
import com.popstack.mvoter2015.domain.location.LocationRepository
import com.popstack.mvoter2015.domain.location.model.Township
import javax.inject.Inject

class GetTownshipsForStateRegion @Inject constructor(
  private val locationRepository: LocationRepository,
  dispatcherProvider: DispatcherProvider
) : CoroutineUseCase<GetTownshipsForStateRegion.Params, List<Township>>(dispatcherProvider) {

  // TODO: Change params according to API changes
  data class Params(
    val stateRegionIdentifier: String
  )

  override suspend fun provide(input: Params): List<Township> {
    return locationRepository.getTownshipsListForStateRegion(input.stateRegionIdentifier)
  }
}