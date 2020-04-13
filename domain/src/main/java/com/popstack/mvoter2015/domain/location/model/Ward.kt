package com.popstack.mvoter2015.domain.location.model

data class Ward(
  val id: WardId,
  val name: String,
  val parentTownshipPCode: TownshipPCode
)