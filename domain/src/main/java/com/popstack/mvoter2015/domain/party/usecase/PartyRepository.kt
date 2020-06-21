package com.popstack.mvoter2015.domain.party.usecase

import com.popstack.mvoter2015.domain.party.model.Party

interface PartyRepository {

  fun getPartyList(page: Int, itemPerPage: Int): List<Party>

}