package com.popstack.mvoter2015.feature.party.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.popstack.mvoter2015.domain.party.model.PartyId
import com.squareup.inject.assisted.Assisted
import com.squareup.inject.assisted.AssistedInject

class PartyDetailViewModel @AssistedInject constructor(
  @Assisted private val params: Params
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
}