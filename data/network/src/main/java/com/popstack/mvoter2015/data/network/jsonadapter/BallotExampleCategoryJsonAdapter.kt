package com.popstack.mvoter2015.data.network.jsonadapter

import com.popstack.mvoter2015.domain.faq.model.BallotExampleCategory
import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson

class BallotExampleCategoryJsonAdapter {

  companion object {
    private const val JSON_STRING_NORMAL = "normal"
    private const val JSON_STRING_ADVANCED = "advanced"
  }

  @ToJson
  fun toJson(value: BallotExampleCategory): String {
    return when (value) {
      BallotExampleCategory.NORMAL -> JSON_STRING_NORMAL
      BallotExampleCategory.ADVANCED -> JSON_STRING_ADVANCED
    }
  }

  @FromJson
  fun fromJson(value: String): BallotExampleCategory {
    return when (value) {
      JSON_STRING_NORMAL -> BallotExampleCategory.NORMAL
      JSON_STRING_ADVANCED -> BallotExampleCategory.ADVANCED
      else -> throw IllegalArgumentException("$value is not part of faq category")
    }
  }
}