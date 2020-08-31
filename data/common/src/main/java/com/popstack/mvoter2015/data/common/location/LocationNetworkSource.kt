package com.popstack.mvoter2015.data.common.location

import com.popstack.mvoter2015.domain.location.model.Township
import com.popstack.mvoter2015.domain.location.model.Ward

interface LocationNetworkSource {
  fun getStateRegionList(): List<String>
  fun getTownshipsListForStateRegion(stateRegionIdentifier: String): List<Township>
  fun getWardsForTownship(stateRegion: String, township: String): List<String>
  fun getWardDetails(stateRegion: String, township: String, wardName: String): Ward
}