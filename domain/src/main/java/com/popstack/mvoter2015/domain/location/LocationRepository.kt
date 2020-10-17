package com.popstack.mvoter2015.domain.location

import com.popstack.mvoter2015.domain.location.model.CombinedLocation
import com.popstack.mvoter2015.domain.location.model.StateRegionTownship
import com.popstack.mvoter2015.domain.location.model.Township
import com.popstack.mvoter2015.domain.location.model.Ward
import kotlinx.coroutines.flow.Flow

interface LocationRepository {

  fun getStateRegionList(): List<String>

  fun getTownshipsListForStateRegion(stateRegionIdentifier: String): List<Township>

  fun getWardsForTownship(
    stateRegion: String,
    township: String
  ): List<String>

  fun getWardDetails(
    stateRegion: String,
    townshipIdentifier: String,
    wardName: String
  ): Ward

  suspend fun saveUserWard(ward: Ward)

  suspend fun getUserWard(): Ward?

  suspend fun saveUserStateRegionTownship(stateRegionTownship: StateRegionTownship)

  suspend fun getUserStateRegionTownship(): StateRegionTownship?

  fun selectedLocationFlow(): Flow<CombinedLocation?>
}