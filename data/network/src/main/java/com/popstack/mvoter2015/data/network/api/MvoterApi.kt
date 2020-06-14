package com.popstack.mvoter2015.data.network.api

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MvoterApi {

  @GET("party")
  fun partyList(
    @Query("page") page: Int
  ): List<PartyApiModel>

  @GET("party/{party_id}")
  fun party(
    @Path("party_id") partyId: String
  ): PartyApiModel

}