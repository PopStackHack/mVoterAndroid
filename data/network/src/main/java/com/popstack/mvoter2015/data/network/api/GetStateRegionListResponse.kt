package com.popstack.mvoter2015.data.network.api

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class GetStateRegionListResponse(
  @Json(name = "data") val data: List<String>
)