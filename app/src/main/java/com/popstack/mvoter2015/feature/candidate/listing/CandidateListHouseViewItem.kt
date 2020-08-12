package com.popstack.mvoter2015.feature.candidate.listing

import android.content.Context
import com.popstack.mvoter2015.R
import com.popstack.mvoter2015.domain.constituency.model.HouseType
import com.popstack.mvoter2015.domain.location.model.StateRegionType
import javax.inject.Inject

internal data class CandidateListHouseViewItem(
  val houseType: HouseType,
  val name: String
)

internal class CandidateListHouseViewItemMapper @Inject constructor(
  private val context: Context
) {

  internal fun mapFromHouseType(
    houseType: HouseType,
    stateRegionType: StateRegionType
  ): CandidateListHouseViewItem {

    val name = when (houseType) {
      HouseType.UPPER_HOUSE -> context.getString(R.string.tab_upper_house)
      HouseType.LOWER_HOUSE -> context.getString(R.string.tab_lower_house)
      HouseType.REGIONAL_HOUSE -> {
        when (stateRegionType) {
          StateRegionType.REGION -> {
            context.getString(R.string.tab_regional_house_region)
          }
          StateRegionType.STATE -> {
            context.getString(R.string.tab_regional_house_state)
          }
        }
      }
    }

    return CandidateListHouseViewItem(
      houseType = houseType,
      name = name
    )
  }
}