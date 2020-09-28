package com.popstack.mvoter2015.domain.constituency.usecase

import com.popstack.mvoter2015.domain.CoroutineUseCase
import com.popstack.mvoter2015.domain.DispatcherProvider
import com.popstack.mvoter2015.domain.constituency.model.Constituency
import com.popstack.mvoter2015.domain.location.LocationRepository
import javax.inject.Inject

class GetMyStateRegionConstituency @Inject constructor(
  dispatcherProvider: DispatcherProvider,
  private val locationRepository: LocationRepository
) : CoroutineUseCase<Unit, Constituency?>(dispatcherProvider) {

  override suspend fun provide(input: Unit): Constituency? {
    return locationRepository.getUserWard()!!.stateRegionConstituency
  }

}