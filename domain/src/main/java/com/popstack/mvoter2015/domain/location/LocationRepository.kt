package com.popstack.mvoter2015.domain.location

import com.popstack.mvoter2015.domain.location.model.Township
import com.popstack.mvoter2015.domain.location.model.Ward

interface LocationRepository {
  fun getStateRegionList(): List<String>
  fun getTownshipsListForStateRegion(stateRegionIdentifier: String): List<Township>
  fun getWardsForTownship(townshipIdentifier: String): List<String>
  fun getWardDetails(townshipIdentifier: String, wardName: String): Ward
}