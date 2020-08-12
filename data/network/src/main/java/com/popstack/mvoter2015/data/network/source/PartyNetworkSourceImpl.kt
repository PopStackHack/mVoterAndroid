package com.popstack.mvoter2015.data.network.source

import com.popstack.mvoter2015.data.common.party.PartyNetworkSource
import com.popstack.mvoter2015.data.network.api.MvoterApi
import com.popstack.mvoter2015.data.network.helper.executeOrThrow
import com.popstack.mvoter2015.domain.party.model.Party
import com.popstack.mvoter2015.domain.party.model.PartyId
import java.time.format.DateTimeFormatter
import java.util.Locale
import javax.inject.Inject

class PartyNetworkSourceImpl @Inject constructor(
  private val mvoterApi: MvoterApi
) : PartyNetworkSource {

  private val dateTimeFormatter = DateTimeFormatter.ofPattern("dd-mm-yyyy", Locale.ENGLISH)

  override fun getPartyList(page: Int, itemPerPage: Int, query: String?): List<Party> {
    return mvoterApi.partyList(page).executeOrThrow().map { apiModel ->
      with(apiModel) {
        Party(
          id = PartyId(registeredNumber.toString()),
          nameBurmese = burmeseName,
          nameEnglish = englishName,
          abbreviation = abbreviation,
          flagImage = flagImageUrl,
          sealImage = sealImageUrl,
          region = region,
          leadersAndChairmenList = leadersAndChairmen,
          memberCount = memberCount,
          number = registeredNumber,
          headquarterLocation = headQuarterLocation,
          policy = policy,
          contacts = contact,
          establishmentApplicationDate = establishmentApplicationDate,
          establishmentApprovalDate = establishmentApprovalDate,
          registrationApplicationDate = registrationApplicationDate,
          registrationApprovalDate = registrationApprovalDate
        )
      }
    }
  }

}