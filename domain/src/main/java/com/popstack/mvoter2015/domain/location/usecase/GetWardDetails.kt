package com.popstack.mvoter2015.domain.location.usecase

import com.popstack.mvoter2015.domain.CoroutineUseCase
import com.popstack.mvoter2015.domain.DispatcherProvider
import com.popstack.mvoter2015.domain.location.LocationRepository
import com.popstack.mvoter2015.domain.location.model.Ward
import javax.inject.Inject

class GetWardDetails @Inject constructor(
  private val locationRepository: LocationRepository,
  dispatcherProvider: DispatcherProvider
) : CoroutineUseCase<GetWardDetails.Params, Ward>(dispatcherProvider) {

  // TODO: Change params according to API changes
  data class Params(
    val stateRegion: String,
    val township: String,
    val ward: String
  )

  override suspend fun provide(input: Params): Ward {
    return locationRepository.getWardDetails(input.stateRegion, input.township, input.ward)
  }
}