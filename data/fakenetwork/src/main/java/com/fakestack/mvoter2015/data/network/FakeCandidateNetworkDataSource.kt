package com.fakestack.mvoter2015.data.network

import android.content.Context
import com.fakestack.mvoter2015.data.network.jsonadapter.LocalDateJsonAdapter
import com.fakestack.mvoter2015.data.network.model.FakeCandidateApiModel
import com.fakestack.mvoter2015.data.network.model.toCandidateModel
import com.popstack.mvoter2015.data.common.candidate.CandidateNetworkSource
import com.popstack.mvoter2015.domain.candidate.model.Candidate
import com.popstack.mvoter2015.domain.candidate.model.CandidateId
import com.popstack.mvoter2015.domain.constituency.model.ConstituencyId
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import java.io.IOException
import javax.inject.Inject

class FakeCandidateNetworkDataSource @Inject constructor(
  private val context: Context
) : CandidateNetworkSource {

  private val fakeCandidateList: List<FakeCandidateApiModel>

  init {
    val moshi = Moshi.Builder()
      .add(LocalDateJsonAdapter())
      .build()

    val listOfCandidate = Types.newParameterizedType(MutableList::class.java, FakeCandidateApiModel::class.java)
    val candidateListAdapter: JsonAdapter<List<FakeCandidateApiModel>> = moshi.adapter(listOfCandidate)

    val upperHouseCandidateListJson = context.resources.openRawResource(R.raw.candidate_upper_house).bufferedReader().use {
      it.readText()
    }
    val upperHouseCandidateList = candidateListAdapter.fromJson(upperHouseCandidateListJson)
      ?: emptyList()

    val lowerHouseCandidateListJson = context.resources.openRawResource(R.raw.candidate_lower_house).bufferedReader().use {
      it.readText()
    }
    val lowerHouseCandidateList = candidateListAdapter.fromJson(lowerHouseCandidateListJson)
      ?: emptyList()

    val stateRegionHouseCandidateListJson = context.resources.openRawResource(R.raw.candidate_state_region).bufferedReader().use {
      it.readText()
    }
    val stateRegionCandidateList = candidateListAdapter.fromJson(stateRegionHouseCandidateListJson)
      ?: emptyList()

    fakeCandidateList = upperHouseCandidateList + lowerHouseCandidateList + stateRegionCandidateList
  }

  override fun getCandidateList(constituencyId: ConstituencyId): List<Candidate> {
    return fakeCandidateList.filter {
      it.attributes.constituency.id == constituencyId.value
    }.map(FakeCandidateApiModel::toCandidateModel)
  }

  override fun getCandidate(candidateId: CandidateId): Candidate {
    return fakeCandidateList.find {
      it.id == candidateId.value
    }?.toCandidateModel() ?: throw IOException()
  }

  override fun searchCandidate(query: String, pageNo: Int, resultsPerPage: Int): List<Candidate> {
    val fromIndex = (pageNo - 1) * resultsPerPage
    val toIndex = fromIndex + resultsPerPage
    val resultList = fakeCandidateList.filter {
      it.attributes.name.contains(query)
    }

    return try {
      resultList.subList(fromIndex, toIndex).map(FakeCandidateApiModel::toCandidateModel)
    } catch (indexOutOfBoundsException: IndexOutOfBoundsException) {
      try {
        resultList.subList(fromIndex, resultList.size).map(FakeCandidateApiModel::toCandidateModel)
      } catch (indexOutOfBoundsException: IndexOutOfBoundsException) {
        emptyList()
      }
    }
  }

}