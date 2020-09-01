package com.popstack.mvoter2015.data.network.api

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ConstituencyApiResponse(
  @Json(name = "id") val id: String,
  @Json(name = "attributes") val attributes: CandidateConstituencyAttributes
)

@JsonClass(generateAdapter = true)
data class CandidateConstituencyAttributes(
  @Json(name = "name") val name: String,
  @Json(name = "house") val house: String
)

@JsonClass(generateAdapter = true)
data class ConstituencyApiModel(
  @Json(name = "id") val id: Long,
  @Json(name = "name") val name: String,
)