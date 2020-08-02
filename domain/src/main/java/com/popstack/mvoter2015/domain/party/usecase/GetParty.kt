package com.popstack.mvoter2015.domain.party.usecase

import com.popstack.mvoter2015.domain.CoroutineUseCase
import com.popstack.mvoter2015.domain.DispatcherProvider
import com.popstack.mvoter2015.domain.party.model.Party
import com.popstack.mvoter2015.domain.party.model.PartyId

class GetParty(dispatcherProvider: DispatcherProvider) :
  CoroutineUseCase<PartyId, Party>(dispatcherProvider) {

  override fun provide(input: PartyId): Party {
    TODO("Not yet implemented")
  }

}