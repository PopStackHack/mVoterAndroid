package com.popstack.mvoter2015.data.network.api

import com.popstack.mvoter2015.domain.constituency.model.Constituency
import com.popstack.mvoter2015.domain.location.model.TownshipPCode
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
        name = lowerHouseConstituency.name
      ),
      upperHouseConstituency = Constituency(
        id = upperHouseConstituency.id.toString(),
        name = upperHouseConstituency.name
      ),
      stateRegionConstituency =  Constituency(
        id = stateRegionConstituency.id.toString(),
        name = stateRegionConstituency.name
      )
    )
  }
}

