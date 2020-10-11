package com.popstack.mvoter2015.data.common.location

import com.popstack.mvoter2015.domain.location.LocationRepository
import com.popstack.mvoter2015.domain.location.model.CombinedLocation
import com.popstack.mvoter2015.domain.location.model.StateRegionTownship
import com.popstack.mvoter2015.domain.location.model.Township
import com.popstack.mvoter2015.domain.location.model.Ward
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocationRepositoryImpl @Inject constructor(
  private val locationNetworkSource: LocationNetworkSource,
  private val locationCacheSource: LocationCacheSource
) : LocationRepository {
  override fun getStateRegionList(): List<String> =
    locationNetworkSource.getStateRegionList()

  override fun getTownshipsListForStateRegion(stateRegionIdentifier: String): List<Township> =
    locationNetworkSource.getTownshipsListForStateRegion(stateRegionIdentifier)

  override fun getWardsForTownship(
    stateRegion: String,
    township: String
  ): List<String> =
    locationNetworkSource.getWardsForTownship(stateRegion, township)

  override fun getWardDetails(
    stateRegion: String,
    townshipIdentifier: String,
    wardName: String
  ): Ward =
    locationNetworkSource.getWardDetails(
      stateRegion = stateRegion, township = townshipIdentifier, wardName = wardName
    )

  override suspend fun saveUserWard(ward: Ward) {
    locationCacheSource.saveUserWard(ward)
  }

  override suspend fun getUserWard(): Ward? {
    return locationCacheSource.getUserWard()
  }

  override suspend fun saveUserStateRegionTownship(stateRegionTownship: StateRegionTownship) {
    locationCacheSource.saveUserStateRegionTownship(stateRegionTownship)
  }

  override suspend fun getUserStateRegionTownship(): StateRegionTownship? {
    return locationCacheSource.getUserStateRegionTownship()
  }

  override fun selectedLocationFlow(): Flow<CombinedLocation?> {
    return locationCacheSource.selectedLocationFlow()
  }
}