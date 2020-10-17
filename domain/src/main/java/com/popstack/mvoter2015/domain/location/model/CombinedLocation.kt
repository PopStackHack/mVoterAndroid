package com.popstack.mvoter2015.domain.location.model

data class CombinedLocation(
  val stateRegion: String,
  val township: String,
  val ward: String?
)