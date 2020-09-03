package com.popstack.mvoter2015.domain.location

import com.popstack.mvoter2015.domain.location.model.StateRegionTownship
import com.popstack.mvoter2015.domain.location.model.Township
import com.popstack.mvoter2015.domain.location.model.Ward

interface LocationRepository {
  fun getStateRegionList(): List<String>
  fun getTownshipsListForStateRegion(stateRegionIdentifier: String): List<Township>
  fun getWardsForTownship(stateRegion: String, township: String): List<String>
  fun getWardDetails(stateRegion: String, townshipIdentifier: String, wardName: String): Ward
  fun saveUserWard(ward: Ward)
  fun getUserWard(): Ward?
  fun saveUserStateRegionTownship(stateRegionTownship: StateRegionTownship)
  fun getUserStateRegionTownship(): StateRegionTownship?
}