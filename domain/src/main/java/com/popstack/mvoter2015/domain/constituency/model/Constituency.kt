package com.popstack.mvoter2015.domain.constituency.model

data class Constituency(
  val id: ConstituencyId,
  val name: String,
  val house: HouseType,
  val remark: String?
)