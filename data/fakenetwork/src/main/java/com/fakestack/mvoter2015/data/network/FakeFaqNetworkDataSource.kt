package com.fakestack.mvoter2015.data.network

import android.content.Context
import com.fakestack.mvoter2015.data.network.jsonadapter.BallotExampleCategoryJsonAdapter
import com.fakestack.mvoter2015.data.network.jsonadapter.FaqCategoryJsonAdapter
import com.fakestack.mvoter2015.data.network.jsonadapter.LocalDateJsonAdapter
import com.fakestack.mvoter2015.data.network.model.FakeBallotExampleApiModel
import com.fakestack.mvoter2015.data.network.model.FakeFaqApiModel
import com.popstack.mvoter2015.data.common.faq.FaqNetworkSource
import com.popstack.mvoter2015.domain.faq.model.BallotExample
import com.popstack.mvoter2015.domain.faq.model.BallotExampleCategory
import com.popstack.mvoter2015.domain.faq.model.Faq
import com.popstack.mvoter2015.domain.faq.model.FaqCategory
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import javax.inject.Inject

class FakeFaqNetworkDataSource @Inject constructor(
  private val context: Context
) : FaqNetworkSource {

  private val fakeFaqList: List<FakeFaqApiModel>
  private val fakeBallotList: List<FakeBallotExampleApiModel>

  init {
    val moshi = Moshi.Builder()
      .add(LocalDateJsonAdapter())
      .add(FaqCategoryJsonAdapter())
      .add(BallotExampleCategoryJsonAdapter())
      .build()

    val faqJson = context.resources.openRawResource(R.raw.faq).bufferedReader().use {
      it.readText()
    }

    val listOfFaq = Types.newParameterizedType(MutableList::class.java, FakeFaqApiModel::class.java)
    val faqListadapter: JsonAdapter<List<FakeFaqApiModel>> = moshi.adapter(listOfFaq)
    fakeFaqList = faqListadapter.fromJson(faqJson) ?: emptyList()

    val ballotExampleJson = context.resources.openRawResource(R.raw.ballot_example).bufferedReader().use {
      it.readText()
    }

    val listOfBallotExample = Types.newParameterizedType(MutableList::class.java, FakeBallotExampleApiModel::class.java)
    val ballotExampleAdapter: JsonAdapter<List<FakeBallotExampleApiModel>> = moshi.adapter(listOfBallotExample)
    fakeBallotList = ballotExampleAdapter.fromJson(ballotExampleJson) ?: emptyList()
  }

  override fun getFaqList(page: Int, itemsPerPage: Int, category: FaqCategory?, query: String?): List<Faq> {
    val fromIndex = (page - 1) * itemsPerPage
    val toIndex = fromIndex + itemsPerPage
    try {
      return fakeFaqList.subList(fromIndex, toIndex).map(FakeFaqApiModel::mapToFaq)
    } catch (indexOutOfBoundsException: IndexOutOfBoundsException) {
      try {
        return fakeFaqList.subList(fromIndex, fakeFaqList.size).map(FakeFaqApiModel::mapToFaq)
      } catch (indexOutOfBoundsException: IndexOutOfBoundsException) {
        return emptyList()
      }
    }
  }

  override fun getBallotExampleList(category: BallotExampleCategory): List<BallotExample> {
    return fakeBallotList.map(FakeBallotExampleApiModel::mapToBallotExample)
  }

}