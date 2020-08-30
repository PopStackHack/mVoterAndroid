package com.popstack.mvoter2015.domain.location.usecase

import com.popstack.mvoter2015.domain.CoroutineUseCase
import com.popstack.mvoter2015.domain.DispatcherProvider
import com.popstack.mvoter2015.domain.location.LocationRepository
import com.popstack.mvoter2015.domain.location.model.Ward
import javax.inject.Inject

class GetWardsForTownship @Inject constructor(
  private val locationRepository: LocationRepository,
  dispatcherProvider: DispatcherProvider
) : CoroutineUseCase<GetWardsForTownship.Params, List<Ward>>(dispatcherProvider) {

  // TODO: Change params according to API changes
  data class Params(
    val townshipIdentifier: String
  )

  override fun provide(input: Params): List<Ward> {
    return locationRepository.getWardsForTownship(input.townshipIdentifier)
  }
}