package com.fakestack.mvoter2015.data.network.model

import com.popstack.mvoter2015.domain.faq.model.BallotExample
import com.popstack.mvoter2015.domain.faq.model.BallotExampleCategory
import com.popstack.mvoter2015.domain.faq.model.BallotExampleId
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class FakeBallotExampleApiModel(
  @Json(name = "id") val ballotExampleId: String,
  @Json(name = "attributes") val attributes: FakeBallotExampleApiAttributes
) {

  fun mapToBallotExample(): BallotExample {
    return BallotExample(
      id = BallotExampleId(ballotExampleId),
      image = attributes.imagePath,
      isValid = attributes.isValid,
      reason = attributes.reason,
      category = attributes.category
    )
  }
}

@JsonClass(generateAdapter = true)
data class FakeBallotExampleApiAttributes(
  @Json(name = "reason") val reason: String?,
  @Json(name = "is_valid") val isValid: Boolean,
  @Json(name = "category") val category: BallotExampleCategory,
  @Json(name = "image_path") val imagePath: String,
)