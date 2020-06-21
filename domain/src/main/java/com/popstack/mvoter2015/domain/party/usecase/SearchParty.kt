package com.popstack.mvoter2015.domain.party.usecase

import com.popstack.mvoter2015.domain.CoroutineUseCase
import com.popstack.mvoter2015.domain.DispatcherProvider
import com.popstack.mvoter2015.domain.party.model.Party
import javax.inject.Inject

class SearchParty @Inject constructor(
  dispatcherProvider: DispatcherProvider,
  private val partyRepository: PartyRepository
) :
  CoroutineUseCase<SearchParty.Params, List<Party>>(
    dispatcherProvider
  ) {

  data class Params(
    val query: String,
    val page: Int,
    val itemPerPage: Int
  )

  override fun provide(input: Params): List<Party> {
    return partyRepository.getPartyList(input.page, input.itemPerPage)
  }
}