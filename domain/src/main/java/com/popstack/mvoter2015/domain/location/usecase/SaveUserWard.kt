package com.popstack.mvoter2015.domain.location.usecase

import com.popstack.mvoter2015.domain.CoroutineUseCase
import com.popstack.mvoter2015.domain.DispatcherProvider
import com.popstack.mvoter2015.domain.location.LocationRepository
import com.popstack.mvoter2015.domain.location.model.Ward
import javax.inject.Inject

class SaveUserWard @Inject constructor(
  private val locationRepository: LocationRepository,
  dispatcherProvider: DispatcherProvider
) : CoroutineUseCase<SaveUserWard.Params, Unit>(dispatcherProvider) {

  // TODO: Change params according to API changes
  data class Params(
    val ward: Ward
  )

  override suspend fun provide(input: Params) {
    locationRepository.saveUserWard(input.ward)
  }
}