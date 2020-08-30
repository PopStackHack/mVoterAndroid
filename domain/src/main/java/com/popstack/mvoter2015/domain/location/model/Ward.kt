package com.popstack.mvoter2015.domain.location.model

import com.popstack.mvoter2015.domain.constituency.model.Constituency

data class Ward(
  val id: WardId,
  val name: String,
  val lowerHouseConstituency: Constituency,
  val upperHouseConstituency: Constituency,
  val stateRegionConstituency: Constituency
)