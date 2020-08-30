package com.popstack.mvoter2015.data.network.api

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ConstituencyApiModel(
  @Json(name = "id") val id: Long,
  @Json(name = "name") val name: String,
)