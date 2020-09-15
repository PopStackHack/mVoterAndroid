package com.popstack.mvoter2015.domain.candidate.usecase

import com.aungkyawpaing.coroutinetestrule.CoroutineTestRule
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.verifyNoMoreInteractions
import com.nhaarman.mockitokotlin2.whenever
import com.popstack.mvoter2015.domain.candidate.CandidateRepository
import com.popstack.mvoter2015.domain.candidate.model.Candidate
import com.popstack.mvoter2015.domain.constituency.model.Constituency
import com.popstack.mvoter2015.domain.constituency.model.ConstituencyId
import com.popstack.mvoter2015.domain.constituency.model.HouseType
import com.popstack.mvoter2015.domain.exception.NetworkException
import com.popstack.mvoter2015.domain.location.LocationRepository
import com.popstack.mvoter2015.domain.location.model.StateRegionTownship
import com.popstack.mvoter2015.domain.location.model.Ward
import com.popstack.mvoter2015.domain.location.model.WardId
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Rule
import org.junit.Test

class GetMyUpperHouseCandidateListBehaviorTest {

  @get:Rule
  var coroutineTestRule = CoroutineTestRule()

  val candidateRepository = mock<CandidateRepository>()
  val locationRepository = mock<LocationRepository>()

  private val getMyUpperHouseCandidateList = GetMyUpperHouseCandidateList(
    coroutineTestRule.testDispatcherProvider,
    candidateRepository,
    locationRepository
  )

  @Test
  fun testConstituencyIdNeedToUpdate() = coroutineTestRule.testDispatcher.runBlockingTest {
    val oldConstituencyId = ConstituencyId("old")
    val newConstituencyId = ConstituencyId("new")
    val newWard = Ward(
      id = WardId("random"),
      name = "name",
      lowerHouseConstituency = Constituency(
        id = "",
        name = "",
        house = HouseType.LOWER_HOUSE,
        township = null,
        stateRegion = null
      ),
      upperHouseConstituency = Constituency(
        id = newConstituencyId.value,
        name = "",
        house = HouseType.UPPER_HOUSE,
        township = null,
        stateRegion = null
      ),
      stateRegionConstituency = Constituency(
        id = "",
        name = "",
        house = HouseType.REGIONAL_HOUSE,
        township = null,
        stateRegion = null
      )
    )

    whenever(candidateRepository.getCandidateList(oldConstituencyId)).then {
      throw NetworkException(errorCode = 404)
    }
    whenever(candidateRepository.getCandidateList(newConstituencyId)).thenReturn(
      emptyList<Candidate>()
    )
    whenever(locationRepository.getUserWard()).thenReturn(
      Ward(
        id = WardId("random"),
        name = "name",
        lowerHouseConstituency = Constituency(
          id = "",
          name = "",
          house = HouseType.LOWER_HOUSE,
          township = null,
          stateRegion = null
        ),
        upperHouseConstituency = Constituency(
          id = oldConstituencyId.value,
          name = "",
          house = HouseType.UPPER_HOUSE,
          township = null,
          stateRegion = null
        ),
        stateRegionConstituency = Constituency(
          id = "",
          name = "",
          house = HouseType.REGIONAL_HOUSE,
          township = null,
          stateRegion = null
        )
      ),
      newWard
    )
    whenever(locationRepository.saveUserWard(any())).then {
      //Do Nothing
    }

    whenever(locationRepository.getWardDetails(any(), any(), any())).thenReturn(
      newWard
    )
    whenever(locationRepository.getUserStateRegionTownship()).thenReturn(
      StateRegionTownship("", "'")
    )

    getMyUpperHouseCandidateList.execute(Unit)

    verify(candidateRepository, times(1)).getCandidateList(oldConstituencyId)
    verify(candidateRepository, times(1)).getCandidateList(newConstituencyId)
    verify(locationRepository, times(1)).getWardDetails(any(), any(), any())
    verify(locationRepository, times(1)).saveUserWard(any())
    verify(locationRepository, times(2)).getUserWard()
  }

  @Test
  fun testConstituencyIdNotNeedToUpdate() = coroutineTestRule.testDispatcher.runBlockingTest {
    val oldConstituencyId = ConstituencyId("old")
    val ward = Ward(
      id = WardId("random"),
      name = "name",
      lowerHouseConstituency = Constituency(
        id = "",
        name = "",
        house = HouseType.LOWER_HOUSE,
        township = null,
        stateRegion = null
      ),
      upperHouseConstituency = Constituency(
        id = oldConstituencyId.value,
        name = "",
        house = HouseType.UPPER_HOUSE,
        township = null,
        stateRegion = null
      ),
      stateRegionConstituency = Constituency(
        id = "",
        name = "",
        house = HouseType.REGIONAL_HOUSE,
        township = null,
        stateRegion = null
      )
    )

    whenever(candidateRepository.getCandidateList(oldConstituencyId)).thenReturn(
      emptyList<Candidate>()
    )
    whenever(locationRepository.getUserWard()).thenReturn(ward)

    getMyUpperHouseCandidateList.execute(Unit)

    verify(candidateRepository, times(1)).getCandidateList(oldConstituencyId)
    verifyNoMoreInteractions(candidateRepository)
    verify(locationRepository, times(1)).getUserWard()
    verifyNoMoreInteractions(locationRepository)
  }
}