package com.popstack.mvoter2015.data.network.api

import com.popstack.mvoter2015.domain.constituency.model.Constituency
import com.popstack.mvoter2015.domain.constituency.model.ConstituencyId
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
    @Json(name = "state_hluttaw_constituency") val stateRegionConstituency: ConstituencyApiModel?
  ) {
    fun toWard(): Ward = Ward(
      id = WardId(id.toString()),
      name = name,
      lowerHouseConstituency = Constituency(
        id = ConstituencyId(lowerHouseConstituency.id.toString()),
        name = lowerHouseConstituency.name,
        remark = lowerHouseConstituency.remark,
        house = HouseType.LOWER_HOUSE,
      ),
      upperHouseConstituency = Constituency(
        id = ConstituencyId(upperHouseConstituency.id.toString()),
        name = upperHouseConstituency.name,
        remark = upperHouseConstituency.remark,
        house = HouseType.UPPER_HOUSE
      ),
      stateRegionConstituency = stateRegionConstituency?.let {
        Constituency(
          id = ConstituencyId(it.id.toString()),
          name = it.name,
          remark = stateRegionConstituency.remark,
          house = HouseType.REGIONAL_HOUSE
        )
      }
    )
  }
}