package com.popstack.mvoter2015.domain.party.usecase

import com.popstack.mvoter2015.domain.CoroutineUseCase
import com.popstack.mvoter2015.domain.DispatcherProvider
import com.popstack.mvoter2015.domain.party.model.Party
import com.popstack.mvoter2015.domain.party.model.PartyId
import javax.inject.Inject

class GetParty @Inject constructor(
  dispatcherProvider: DispatcherProvider,
  private val partyRepository: PartyRepository
) : CoroutineUseCase<PartyId, Party>(dispatcherProvider) {

  override suspend fun provide(input: PartyId): Party {
    return partyRepository.getParty(input)
  }

}