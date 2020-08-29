package com.popstack.mvoter2015.feature.party.detail

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.popstack.mvoter2015.domain.party.model.PartyId
import com.popstack.mvoter2015.domain.party.usecase.GetParty
import com.popstack.mvoter2015.exception.GlobalExceptionHandler
import com.popstack.mvoter2015.helper.asyncviewstate.AsyncViewStateLiveData
import com.popstack.mvoter2015.helper.format
import kotlinx.coroutines.launch
import timber.log.Timber

class PartyDetailViewModel @ViewModelInject constructor(
  private val getParty: GetParty,
  private val globalExceptionHandler: GlobalExceptionHandler
) : ViewModel() {

  private var _partyId: PartyId? = null
  private val partyId: PartyId
    get() = _partyId!!

  fun setPartyId(partyId: PartyId) {
    this._partyId = partyId
  }

  val viewItemLiveData = AsyncViewStateLiveData<PartyDetailViewItem>()

  fun loadData() {
    viewModelScope.launch {
      viewItemLiveData.postLoading()

      kotlin.runCatching {
        //TODO:
        val party = getParty.execute(partyId)

        val timelineList = mutableListOf<PartyTimelineViewItem>()


        party.establishmentApplicationDate?.let { establishmentApplicationDate ->
          timelineList.add(
            PartyTimelineViewItem(
              date = establishmentApplicationDate,
              event = TimelineEvent.ESTABLISHMENT_APPLICATION
            )
          )
        }

        party.establishmentApprovalDate?.let { establishmentApprovalDate ->
          timelineList.add(
            PartyTimelineViewItem(
              date = establishmentApprovalDate,
              event = TimelineEvent.ESTABLISHMENT_APPROVAL
            )
          )
        }

        party.registrationApplicationDate?.let { registrationApplicationDate ->
          timelineList.add(
            PartyTimelineViewItem(
              date = registrationApplicationDate,
              event = TimelineEvent.REGISTRATION_APPLICATION
            )
          )
        }

        party.registrationApprovalDate?.let { registrationApprovalDate ->
          timelineList.add(
            PartyTimelineViewItem(
              date = registrationApprovalDate,
              event = TimelineEvent.REGISTRATION_APPROVAL
            )
          )
        }

        val viewItem = with(party) {
          PartyDetailViewItem(
            partyId = id,
            name = nameBurmese,
            nameEnglish = nameEnglish,
            nameAbbreviation = abbreviation,
            region = region,
            policy = policy,
            flagImage = flagImage,
            sealImage = sealImage,
            partyNumber = registeredNumber.toString(),
            leadersAndChairmen = leadersAndChairmenList.format("၊"),
            memberCount = memberCount?.toString() ?: "-",
            headQuarterLocation = headquarterLocation,
            contact = contacts.format("၊"),
            isPoteMa25 = establishmentApplicationDate == null || establishmentApprovalDate == null,
            timeline = timelineList.sortedBy { it.date }
          )
        }

        viewItemLiveData.postSuccess(viewItem)

      }.exceptionOrNull()?.let { exception ->
        Timber.e(exception)
        viewItemLiveData.postError(exception, globalExceptionHandler.getMessageForUser(exception))
      }

    }
  }
}