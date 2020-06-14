package com.popstack.mvoter2015.data.common.party

interface PartyNetworkSource {

  fun getPartyList(page: Int, itemPerPage: Int): List<PartyEntity>
}