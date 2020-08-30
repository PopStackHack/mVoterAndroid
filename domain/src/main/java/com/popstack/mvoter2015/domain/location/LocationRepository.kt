package com.popstack.mvoter2015.domain.location

import com.popstack.mvoter2015.domain.location.model.StateRegion
import com.popstack.mvoter2015.domain.location.model.Township
import com.popstack.mvoter2015.domain.location.model.Ward

interface LocationRepository {
  fun getStateRegionList(): List<StateRegion>
  fun getTownshipsListForStateRegion(stateRegionIdentifier: String): List<Township>
  fun getWardsForTownship(townshipIdentifier: String): List<Ward>
}