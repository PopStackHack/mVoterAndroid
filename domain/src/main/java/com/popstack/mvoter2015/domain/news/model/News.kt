package com.popstack.mvoter2015.domain.news.model

import java.time.LocalDate

data class News(
  val id: NewsId,
  val title: String,
  val summary: String,
  val body: String?,
  val imageUrl: String?,
  val publishedDate: LocalDate,
  val url: String
)