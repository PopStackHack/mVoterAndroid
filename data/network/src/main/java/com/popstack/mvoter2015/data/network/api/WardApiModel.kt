package com.popstack.mvoter2015.data.network.api

import com.popstack.mvoter2015.domain.constituency.model.Constituency
import com.popstack.mvoter2015.domain.constituency.model.HouseType
import com.popstack.mvoter2015.domain.location.model.Ward
import com.popstack.mvoter2015.domain.location.model.WardId
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class GetWardListResponse(
  @Json(name = "data") val data: List<String>
)

@JsonClass(generateAdapter = true)
data class WardApiModel(
  @Json(name = "data") val data: WardAttributes
) {
  @JsonClass(generateAdapter = true)
  data class WardAttributes(
    @Json(name = "attributes") val wardAttributes: Attributes
  )

  @JsonClass(generateAdapter = true)
  data class Attributes(
    @Json(name = "id") val id: Long,
    @Json(name = "name") val name: String,
    @Json(name = "parent_township") val parentTownship: String,
    @Json(name = "pyithu_hluttaw_constituency") val lowerHouseConstituency: ConstituencyApiModel,
    @Json(name = "amyotha_hluttaw_constituency") val upperHouseConstituency: ConstituencyApiModel,
    @Json(name = "state_hluttaw_constituency") val stateRegionConstituency: ConstituencyApiModel
  ) {
    fun toWard(): Ward = Ward(
      id = WardId(id.toString()),
      name = name,
      lowerHouseConstituency = Constituency(
        id = lowerHouseConstituency.id.toString(),
        name = lowerHouseConstituency.name,
        house = HouseType.LOWER_HOUSE,
        null, null
      ),
      upperHouseConstituency = Constituency(
        id = upperHouseConstituency.id.toString(),
        name = upperHouseConstituency.name,
        house = HouseType.UPPER_HOUSE,
        null, null
      ),
      stateRegionConstituency = Constituency(
        id = stateRegionConstituency.id.toString(),
        name = stateRegionConstituency.name,
        house = HouseType.REGIONAL_HOUSE,
        null, null
      )
    )
  }
}