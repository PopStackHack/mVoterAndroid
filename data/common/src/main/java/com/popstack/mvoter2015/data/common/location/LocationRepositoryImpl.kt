package com.popstack.mvoter2015.data.common.location

import com.popstack.mvoter2015.domain.location.LocationRepository
import com.popstack.mvoter2015.domain.location.model.Township
import com.popstack.mvoter2015.domain.location.model.Ward
import javax.inject.Inject

class LocationRepositoryImpl @Inject constructor(
  private val locationNetworkSource: LocationNetworkSource,
  private val locationCacheSource: LocationCacheSource
) : LocationRepository {
  override fun getStateRegionList(): List<String> =
    locationNetworkSource.getStateRegionList()

  override fun getTownshipsListForStateRegion(stateRegionIdentifier: String): List<Township> =
    locationNetworkSource.getTownshipsListForStateRegion(stateRegionIdentifier)

  override fun getWardsForTownship(stateRegion: String, township: String): List<String> =
    locationNetworkSource.getWardsForTownship(stateRegion, township)

  override fun getWardDetails(stateRegion: String, townshipIdentifier: String, wardName: String): Ward =
    locationNetworkSource.getWardDetails(stateRegion = stateRegion, township = townshipIdentifier, wardName = wardName)

  override fun saveUserWard(ward: Ward) {
    locationCacheSource.saveUserWard(ward)
  }

  override fun getUserWard(): Ward? {
    return locationCacheSource.getUserWard()
  }
}