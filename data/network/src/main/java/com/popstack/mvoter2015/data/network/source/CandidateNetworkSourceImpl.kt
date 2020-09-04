package com.popstack.mvoter2015.data.network.source

import com.popstack.mvoter2015.data.common.candidate.CandidateNetworkSource
import com.popstack.mvoter2015.data.network.api.MvoterApi
import com.popstack.mvoter2015.data.network.api.toCandidateModel
import com.popstack.mvoter2015.data.network.exception.NetworkException
import com.popstack.mvoter2015.data.network.helper.executeOrThrow
import com.popstack.mvoter2015.domain.candidate.model.Candidate
import com.popstack.mvoter2015.domain.candidate.model.CandidateId
import com.popstack.mvoter2015.domain.constituency.model.ConstituencyId
import com.popstack.mvoter2015.domain.constituency.model.HouseType
import com.popstack.mvoter2015.domain.location.LocationRepository
import java.net.HttpURLConnection
import javax.inject.Inject

class CandidateNetworkSourceImpl @Inject constructor(
  private val mvoterApi: MvoterApi,
  private val locationRepository: LocationRepository
) : CandidateNetworkSource {

  override fun getCandidateList(constituencyId: ConstituencyId): List<Candidate> {
    try {
      return mvoterApi.candidateList(constituencyId.value).executeOrThrow().data.map { it.toCandidateModel() }
    } catch (networkException: NetworkException) {
      // TODO : This block should be in repo. Move to repo
      if (networkException.errorCode == HttpURLConnection.HTTP_NOT_FOUND) {
        val userWard = locationRepository.getUserWard()

        val houseType = when (constituencyId.value) {
          userWard?.upperHouseConstituency?.id -> {
            HouseType.UPPER_HOUSE
          }
          userWard?.lowerHouseConstituency?.id -> {
            HouseType.LOWER_HOUSE
          }
          else -> {
            HouseType.REGIONAL_HOUSE
          }
        }

        val userStateRegionTownship = locationRepository.getUserStateRegionTownship()

        val wardDetails = locationRepository.getWardDetails(
          stateRegion = userStateRegionTownship?.stateRegion!!,
          townshipIdentifier = userStateRegionTownship.township,
          wardName = userWard?.name!!
        )

        locationRepository.saveUserWard(wardDetails)

        val updatedConstituencyId = when (houseType) {
          HouseType.UPPER_HOUSE -> wardDetails.upperHouseConstituency.id
          HouseType.LOWER_HOUSE -> wardDetails.lowerHouseConstituency.id
          HouseType.REGIONAL_HOUSE -> wardDetails.stateRegionConstituency.id
        }

        return mvoterApi.candidateList(updatedConstituencyId).executeOrThrow().data.map { it.toCandidateModel() }
      } else {
        throw networkException
      }
    }
  }

  override fun getCandidate(candidateId: CandidateId): Candidate {
    return mvoterApi.candidate(candidateId.value).executeOrThrow().data.toCandidateModel()
  }

  override fun searchCandidate(query: String, pageNo: Int, resultsPerPage: Int): List<Candidate> {
    return mvoterApi.searchCandidates(query, pageNo).executeOrThrow().data.map { it.toCandidateModel() }
  }

}