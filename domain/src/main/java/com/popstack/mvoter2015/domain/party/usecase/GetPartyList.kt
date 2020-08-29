package com.popstack.mvoter2015.domain.party.usecase

import com.popstack.mvoter2015.domain.CoroutineUseCase
import com.popstack.mvoter2015.domain.DispatcherProvider
import com.popstack.mvoter2015.domain.party.model.Party
import javax.inject.Inject

class GetPartyList @Inject constructor(
  dispatcherProvider: DispatcherProvider,
  private val partyRepository: PartyRepository
) :
  CoroutineUseCase<GetPartyList.Params, List<Party>>(
    dispatcherProvider
  ) {

  data class Params(
    val page: Int,
    val itemPerPage: Int
  )

  override suspend fun provide(input: Params): List<Party> {
    return partyRepository.getPartyList(input.page, input.itemPerPage)
  }
}