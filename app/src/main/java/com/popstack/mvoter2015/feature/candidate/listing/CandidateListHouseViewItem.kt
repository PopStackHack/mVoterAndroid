package com.popstack.mvoter2015.feature.candidate.listing

import android.content.Context
import com.popstack.mvoter2015.R
import com.popstack.mvoter2015.domain.constituency.model.HouseType
import com.popstack.mvoter2015.domain.location.model.Ward
import javax.inject.Inject

data class CandidateListHouseViewItem(
  val houseType: HouseType,
  val name: String,
  val constituencyId: String
)

class CandidateListHouseViewItemMapper @Inject constructor(
  private val context: Context
) {

  internal fun mapFromHouseType(
    houseType: HouseType,
    userWard: Ward
  ): CandidateListHouseViewItem {

    val name = when (houseType) {
      HouseType.UPPER_HOUSE -> context.getString(R.string.tab_upper_house)
      HouseType.LOWER_HOUSE -> context.getString(R.string.tab_lower_house)
      HouseType.REGIONAL_HOUSE -> {
        if (userWard.stateRegionConstituency.name.contains("တိုင်းဒေသကြီး"))
          context.getString(R.string.tab_regional_house_state)
        else
          context.getString(R.string.tab_regional_house_region)
      }
    }

    val id = when (houseType) {
      HouseType.UPPER_HOUSE -> userWard.upperHouseConstituency.id
      HouseType.LOWER_HOUSE -> userWard.lowerHouseConstituency.id
      HouseType.REGIONAL_HOUSE -> userWard.stateRegionConstituency.id
    }

    return CandidateListHouseViewItem(
      houseType = houseType,
      name = name,
      constituencyId = id
    )
  }
}