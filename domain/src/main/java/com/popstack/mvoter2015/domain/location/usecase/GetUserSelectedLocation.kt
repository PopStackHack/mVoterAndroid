package com.popstack.mvoter2015.domain.location.usecase

import com.popstack.mvoter2015.domain.DispatcherProvider
import com.popstack.mvoter2015.domain.location.FlowUseCase
import com.popstack.mvoter2015.domain.location.LocationRepository
import com.popstack.mvoter2015.domain.location.model.CombinedLocation
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetUserSelectedLocation @Inject constructor(
  dispatcherProvider: DispatcherProvider,
  private val locationRepository: LocationRepository
) : FlowUseCase<Unit, CombinedLocation?>(dispatcherProvider) {

  override fun provide(params: Unit): Flow<CombinedLocation?> {
    return locationRepository.selectedLocationFlow()
  }

}