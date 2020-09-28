package com.popstack.mvoter2015.data.network.api

import com.popstack.mvoter2015.domain.faq.model.BallotExample
import com.popstack.mvoter2015.domain.faq.model.BallotExampleCategory
import com.popstack.mvoter2015.domain.faq.model.BallotExampleId
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class GetBallotExampleListResponse(
  @Json(name = "data") val data: List<BallotExampleApiModel>
)

@JsonClass(generateAdapter = true)
data class BallotExampleApiModel(
  @Json(name = "id") val ballotExampleId: String,
  @Json(name = "attributes") val attributes: BallotExampleApiAttributes
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
data class BallotExampleApiAttributes(
  @Json(name = "reason") val reason: String?,
  @Json(name = "is_valid") val isValid: Boolean,
  @Json(name = "category") val category: BallotExampleCategory,
  @Json(name = "image_path") val imagePath: String,
)

//"id": 1,
//"reason": "ကိုယ်စားလှယ်လောင်း တစ်ဦးကိုသာ ဆန္ဒပြုထားကြောင်း ထင်ရှားသည့်မဲ",
//"is_valid": true,
//"category": "normal",
//"image_path": "http://mvoter.kwee.online/ballots/valid_pics/valid01.png"