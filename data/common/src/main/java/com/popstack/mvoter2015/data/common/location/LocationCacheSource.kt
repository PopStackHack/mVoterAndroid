package com.popstack.mvoter2015.data.common.location

import com.popstack.mvoter2015.domain.location.model.StateRegionTownship
import com.popstack.mvoter2015.domain.location.model.Ward

interface LocationCacheSource {

  suspend fun getUserWard(): Ward?

  suspend fun saveUserWard(ward: Ward)

  suspend fun getUserStateRegionTownship(): StateRegionTownship?

  suspend fun saveUserStateRegionTownship(stateRegionTownship: StateRegionTownship)

}