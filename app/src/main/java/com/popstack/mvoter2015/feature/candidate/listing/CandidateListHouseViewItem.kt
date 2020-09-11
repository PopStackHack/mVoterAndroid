package com.popstack.mvoter2015.feature.candidate.listing

import android.content.Context
import com.popstack.mvoter2015.R
import com.popstack.mvoter2015.domain.constituency.model.HouseType
import com.popstack.mvoter2015.domain.location.model.Ward
import javax.inject.Inject

data class CandidateListHouseViewItem(
  val houseName: String,
  val constituencyId: String,
  val constituencyName: String
)

class CandidateListHouseViewItemMapper @Inject constructor(
  private val context: Context
) {

  internal fun mapFromHouseType(
    houseType: HouseType,
    userWard: Ward
  ): CandidateListHouseViewItem {

    lateinit var houseName: String
    lateinit var constituencyName: String

    when (houseType) {
      HouseType.UPPER_HOUSE -> {
        houseName = context.getString(R.string.tab_upper_house)
        constituencyName = userWard.upperHouseConstituency.name
      }
      HouseType.LOWER_HOUSE -> {
        houseName = context.getString(R.string.tab_lower_house)
        constituencyName = userWard.lowerHouseConstituency.name
      }
      HouseType.REGIONAL_HOUSE -> {
        houseName = if (userWard.stateRegionConstituency.name.contains("တိုင်းဒေသကြီး"))
          context.getString(R.string.tab_regional_house_state)
        else
          context.getString(R.string.tab_regional_house_region)
        constituencyName = userWard.stateRegionConstituency.name
      }
    }

    val id = when (houseType) {
      HouseType.UPPER_HOUSE -> userWard.upperHouseConstituency.id
      HouseType.LOWER_HOUSE -> userWard.lowerHouseConstituency.id
      HouseType.REGIONAL_HOUSE -> userWard.stateRegionConstituency.id
    }

    return CandidateListHouseViewItem(
      houseName = houseName,
      constituencyId = id,
      constituencyName = constituencyName
    )
  }
}