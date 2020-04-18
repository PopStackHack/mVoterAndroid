package com.popstack.mvoter2015.domain.party.usecase

import com.popstack.mvoter2015.domain.CoroutineUseCase
import com.popstack.mvoter2015.domain.DispatcherProvider
import com.popstack.mvoter2015.domain.party.model.Party
import javax.inject.Inject

class GetPartyList @Inject constructor(dispatcherProvider: DispatcherProvider) : CoroutineUseCase<GetPartyList.Params, List<Party>>(
  dispatcherProvider
) {

  data class Params(
    val page: Int
  )

  override fun provide(input: Params): List<Party> {
    TODO("Not yet implemented")
  }
}