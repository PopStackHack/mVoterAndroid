package com.fakestack.mvoter2015.data.network

import android.content.Context
import com.fakestack.mvoter2015.data.network.jsonadapter.LocalDateJsonAdapter
import com.fakestack.mvoter2015.data.network.model.FakeNewsApiModel
import com.popstack.mvoter2015.data.common.news.NewsNetworkSource
import com.popstack.mvoter2015.domain.news.model.News
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import javax.inject.Inject

class FakeNewsNetworkDataSource @Inject constructor(
  private val context: Context
) : NewsNetworkSource {

  private val fakeNewsList: List<FakeNewsApiModel>

  init {
    val moshi = Moshi.Builder()
      .add(LocalDateJsonAdapter())
      .build()
    val json = context.resources.openRawResource(R.raw.news).bufferedReader().use {
      it.readText()
    }

    val listOfNews = Types.newParameterizedType(MutableList::class.java, FakeNewsApiModel::class.java)
    val adapter: JsonAdapter<List<FakeNewsApiModel>> = moshi.adapter(listOfNews)
    fakeNewsList = adapter.fromJson(json) ?: emptyList()
  }

  override fun getNewsList(page: Int, itemPerPage: Int, query: String?): List<News> {
    val fromIndex = (page - 1) * itemPerPage
    val toIndex = fromIndex + itemPerPage
    try {
      return fakeNewsList.subList(fromIndex, toIndex).map(FakeNewsApiModel::mapToNews)
    } catch (indexOutOfBoundsException: IndexOutOfBoundsException) {
      try {
        return fakeNewsList.subList(fromIndex, fakeNewsList.size).map(FakeNewsApiModel::mapToNews)
      } catch (indexOutOfBoundsException: IndexOutOfBoundsException) {
        return emptyList()
      }
    }
  }

}