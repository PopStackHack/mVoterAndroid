package com.popstack.mvoter2015.data.network.api

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MvoterApi {

  @GET("parties")
  fun partyList(
    @Query("page") page: Int,
    @Query("query") query: String?
  ): Call<GetPartyListResponse>

  @GET("parties/{party_id}")
  fun party(
    @Path("party_id") partyId: String
  ): Call<PartyApiModel>

}