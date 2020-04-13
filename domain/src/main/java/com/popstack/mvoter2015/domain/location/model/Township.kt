package com.popstack.mvoter2015.domain.location.model

data class Township(
  val pCode: TownshipPCode,
  val name: String,
  val parentStateRegionPCode: StateRegionPCode
)