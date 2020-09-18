package com.popstack.mvoter2015.data.network.jsonadapter

import com.popstack.mvoter2015.domain.faq.model.FaqCategory
import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson

class FaqCategoryJsonAdapter {

  companion object {
    private const val JSON_STRING_VOTER_LIST = "voter_list"
    private const val JSON_STRING_DIPLOMATC = "diplomatic"
    private const val JSON_STRING_INTERNATIONAL_OBSERVER = "international_observer"
    private const val JSON_STRING_CANDIDATE = "candidate"
    private const val JSON_STRING_CONFLICT_RESOLUTION = "conflict_resolution"
    private const val JSON_STRING_MEDIATION_COMMITTEES = "mediation_committees"

  }

  @ToJson
  fun toJson(value: FaqCategory): String {
    return when (value) {
      FaqCategory.VOTER_LIST -> JSON_STRING_VOTER_LIST
      FaqCategory.DIPLOMATIC -> JSON_STRING_DIPLOMATC
      FaqCategory.INTERNATIONAL_OBSERVER -> JSON_STRING_INTERNATIONAL_OBSERVER
      FaqCategory.CANDIDATE -> JSON_STRING_CANDIDATE
      FaqCategory.CONFLICT_RESOLUTION -> JSON_STRING_CONFLICT_RESOLUTION
      FaqCategory.MEDIATION_COMMITTEES -> JSON_STRING_MEDIATION_COMMITTEES
    }
  }

  @FromJson
  fun fromJson(value: String): FaqCategory {
    return when (value) {
      JSON_STRING_VOTER_LIST -> FaqCategory.VOTER_LIST
      JSON_STRING_DIPLOMATC -> FaqCategory.DIPLOMATIC
      JSON_STRING_INTERNATIONAL_OBSERVER -> FaqCategory.INTERNATIONAL_OBSERVER
      JSON_STRING_CANDIDATE -> FaqCategory.CANDIDATE
      JSON_STRING_CONFLICT_RESOLUTION -> FaqCategory.CONFLICT_RESOLUTION
      JSON_STRING_MEDIATION_COMMITTEES -> FaqCategory.MEDIATION_COMMITTEES
      else -> throw IllegalArgumentException("$value is not part of faq category")
    }
  }
}