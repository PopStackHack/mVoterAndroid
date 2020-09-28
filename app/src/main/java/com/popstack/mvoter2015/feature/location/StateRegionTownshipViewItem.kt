package com.popstack.mvoter2015.feature.location

data class StateRegionTownshipViewItem(
  val name: String,
  val isSelected: Boolean = false,
  val isLoading: Boolean = false,
  val error: String = "",
  val townshipList: List<TownshipViewItem> = ArrayList()
)