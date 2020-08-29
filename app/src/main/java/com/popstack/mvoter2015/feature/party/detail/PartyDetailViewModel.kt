package com.popstack.mvoter2015.feature.party.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.popstack.mvoter2015.domain.party.model.PartyId
import com.popstack.mvoter2015.domain.party.usecase.GetParty
import com.popstack.mvoter2015.exception.GlobalExceptionHandler
import com.popstack.mvoter2015.helper.asyncviewstate.AsyncViewStateLiveData
import com.popstack.mvoter2015.helper.format
import com.squareup.inject.assisted.Assisted
import com.squareup.inject.assisted.AssistedInject
import kotlinx.coroutines.launch
import timber.log.Timber

class PartyDetailViewModel @AssistedInject constructor(
  @Assisted private val params: Params,
  private val getParty: GetParty,
  private val globalExceptionHandler: GlobalExceptionHandler
) : ViewModel() {

  data class Params(
    val partyId: PartyId
  )

  private val partyId = params.partyId

  @AssistedInject.Factory
  interface Factory {
    fun create(params: Params): PartyDetailViewModel
  }

  companion object {
    fun provideFactory(
      factory: Factory,
      params: Params
    ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
      override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return factory.create(params) as T
      }
    }
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