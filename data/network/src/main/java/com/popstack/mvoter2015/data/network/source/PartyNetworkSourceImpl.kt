package com.popstack.mvoter2015.data.network.source

import com.popstack.mvoter2015.data.common.party.PartyNetworkSource
import com.popstack.mvoter2015.data.network.api.MvoterApi
import com.popstack.mvoter2015.domain.party.model.Party
import com.popstack.mvoter2015.domain.party.model.PartyId
import javax.inject.Inject

class PartyNetworkSourceImpl @Inject constructor(
  private val mvoterApi: MvoterApi
) : PartyNetworkSource {

  override fun getPartyList(page: Int, itemPerPage: Int): List<Party> {
    return mvoterApi.partyList(page).map { apiModel ->
      with(apiModel) {
        Party(
          id = PartyId(id),
          englishName = englishName,
          burmeseName = burmeseName,
          abbreviation = abbreviation,
          flagUrl = flagImageUrl,
          sealUrl = sealImageUrl,
          region = region,
          chairmanList = chairmans,
          leaderList = leaders,
          memberCount = memberCount,
          headquarterLocation = headQuarterLocation,
          policy = policy,
          registrationApplicationDate = registrationApplicationDate,
          registrationApprovalDate = registrationApprovalDate,
          establishmentApprovalDate = establishmentApprovalDate
        )
      }
    }
  }

}