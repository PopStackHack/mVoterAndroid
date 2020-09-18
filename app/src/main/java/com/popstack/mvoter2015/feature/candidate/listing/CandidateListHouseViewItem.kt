package com.popstack.mvoter2015.feature.candidate.listing

import android.content.Context
import com.popstack.mvoter2015.R
import com.popstack.mvoter2015.domain.constituency.model.HouseType
import com.popstack.mvoter2015.domain.location.model.Ward
import javax.inject.Inject

data class CandidateListHouseViewItem(
  val houseName: String,
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
        houseName = if (userWard.stateRegionConstituency?.name?.contains("ပြည်နယ်") == true)
          context.getString(R.string.tab_regional_house_region)
        else
          context.getString(R.string.tab_regional_house_state)
        constituencyName = userWard.stateRegionConstituency?.name ?: ""
      }
    }

    return CandidateListHouseViewItem(
      houseName = houseName,
      constituencyName = constituencyName
    )
  }
}