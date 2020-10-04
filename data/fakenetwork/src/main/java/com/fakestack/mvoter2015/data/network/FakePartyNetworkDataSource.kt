package com.fakestack.mvoter2015.data.network

import android.content.Context
import com.fakestack.mvoter2015.data.network.jsonadapter.LocalDateJsonAdapter
import com.fakestack.mvoter2015.data.network.model.FakePartyApiModel
import com.popstack.mvoter2015.data.common.party.PartyNetworkSource
import com.popstack.mvoter2015.domain.party.model.Party
import com.popstack.mvoter2015.domain.party.model.PartyId
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import java.io.IOException
import javax.inject.Inject

class FakePartyNetworkDataSource @Inject constructor(
  private val context: Context
) : PartyNetworkSource {

  private val fakePartyList: List<FakePartyApiModel>

  init {
    val moshi = Moshi.Builder()
      .add(LocalDateJsonAdapter())
      .build()
    val json = context.resources.openRawResource(R.raw.party).bufferedReader().use {
      it.readText()
    }

    val listOfParty = Types.newParameterizedType(MutableList::class.java, FakePartyApiModel::class.java)
    val adapter: JsonAdapter<List<FakePartyApiModel>> = moshi.adapter(listOfParty)
    fakePartyList = adapter.fromJson(json) ?: emptyList()
  }

  override fun getPartyList(page: Int, itemPerPage: Int, query: String?): List<Party> {
    val fromIndex = (page - 1) * itemPerPage
    val toIndex = fromIndex + itemPerPage
    try {
      return fakePartyList.subList(fromIndex, toIndex).map(FakePartyApiModel::mapToParty)
    } catch (indexOutOfBoundsException: IndexOutOfBoundsException) {
      try {
        return fakePartyList.subList(fromIndex, fakePartyList.size).map(FakePartyApiModel::mapToParty)
      } catch (indexOutOfBoundsException: IndexOutOfBoundsException) {
        return emptyList()
      }
    }
  }

  override fun getParty(input: PartyId): Party {
    return fakePartyList.find { it.partyId == input.value }?.mapToParty() ?: throw IOException()
  }

}