package com.popstack.mvoter2015.data.common.location

import com.popstack.mvoter2015.domain.location.LocationRepository
import com.popstack.mvoter2015.domain.location.model.Township
import com.popstack.mvoter2015.domain.location.model.Ward
import javax.inject.Inject

class LocationRepositoryImpl @Inject constructor(
  private val locationNetworkSource: LocationNetworkSource
) : LocationRepository {
  override fun getStateRegionList(): List<String> =
    locationNetworkSource.getStateRegionList()

  override fun getTownshipsListForStateRegion(stateRegionIdentifier: String): List<Township> =
    locationNetworkSource.getTownshipsListForStateRegion(stateRegionIdentifier)

  override fun getWardsForTownship(townshipIdentifier: String): List<String> =
    locationNetworkSource.getWardsForTownship(townshipIdentifier)

  override fun getWardDetails(townshipIdentifier: String, wardName: String): Ward =
    locationNetworkSource.getWardDetails(townshipIdentifier = townshipIdentifier, wardName = wardName)
}