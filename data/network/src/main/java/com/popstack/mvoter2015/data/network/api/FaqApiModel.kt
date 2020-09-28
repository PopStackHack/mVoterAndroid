package com.popstack.mvoter2015.data.network.api

import com.popstack.mvoter2015.domain.faq.model.Faq
import com.popstack.mvoter2015.domain.faq.model.FaqCategory
import com.popstack.mvoter2015.domain.faq.model.FaqId
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class GetFaqListResponse(
  @Json(name = "data") val data: List<FaqApiModel>
)

@JsonClass(generateAdapter = true)
data class FaqApiModel(
  @Json(name = "id") val faqId: String,
  @Json(name = "attributes") val attributes: FaqApiAttributes
) {

  fun mapToFaq(): Faq {
    return Faq(
      id = FaqId(faqId),
      question = attributes.question,
      answer = attributes.answer,
      lawSource = attributes.lawSource,
      articleSource = attributes.articleSource,
      category = attributes.category
    )
  }
}

@JsonClass(generateAdapter = true)
data class FaqApiAttributes(
  @Json(name = "category") val category: FaqCategory,
  @Json(name = "question") val question: String,
  @Json(name = "answer") val answer: String,
  @Json(name = "source") val source: String?,
  @Json(name = "law_source") val lawSource: String?,
  @Json(name = "article_source") val articleSource: String?
)