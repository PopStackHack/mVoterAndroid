package com.popstack.mvoter2015.domain.constituency.repository

import com.popstack.mvoter2015.domain.constituency.model.Constituency

interface ConstituencyRepository {

  fun getUserLowerHouseConstituency(): Constituency

  fun getUserUpperHouseConstituency(): Constituency

  fun getUserStateRegionHouseConstituency(): Constituency

}