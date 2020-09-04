package com.popstack.mvoter2015.data.common.location

import com.popstack.mvoter2015.domain.location.model.StateRegionTownship
import com.popstack.mvoter2015.domain.location.model.Ward

interface LocationCacheSource {
  fun getUserWard(): Ward?
  fun saveUserWard(ward: Ward)
  fun getUserStateRegionTownship(): StateRegionTownship?
  fun saveUserStateRegionTownship(stateRegionTownship: StateRegionTownship)
}