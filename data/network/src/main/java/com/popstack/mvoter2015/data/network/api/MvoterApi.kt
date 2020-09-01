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
  ): Call<GetPartyDetailResponse>

  @GET("faqs")
  fun faqList(
    @Query("page") page: Int,
    @Query("category") category: String?,
    @Query("query") query: String?
  ): Call<GetFaqListResponse>

  @GET("news")
  fun newsList(
    @Query("page") page: Int,
    @Query("items_per_page") itemPerPage: Int,
    @Query("query") query: String?
  ): Call<GetNewsListResponse>

  @GET("locality/state_regions")
  fun getStateRegionList(): Call<GetStateRegionListResponse>

  @GET("locality/townships")
  fun getTownshipsForStateRegion(
    @Query("state_region") stateRegion: String
  ): Call<GetTownshipListResponse>

  @GET("locality/wards")
  fun getWardsForTownship(
    @Query("state_region") stateRegion: String,
    @Query("township") township: String
  ): Call<GetWardListResponse>

  @GET("locality/details")
  fun getWardDetails(
    @Query("state_region") stateRegion: String,
    @Query("township") township: String,
    @Query("ward") ward: String
  ): Call<WardApiModel>

  @GET("candidates")
  fun candidateList(
    @Query("constituency_id") constituencyId: String
  ): Call<CandidateListApiResponse>

  @GET("candidates/{candidate_id}")
  fun candidate(
    @Path("candidate_id") candidateId: String
  ): Call<CandidateDetailsApiResponse>

  @GET("candidates")
  fun searchCandidates(
    @Query("query") query: String,
    @Query("page") page: Int
  ): Call<CandidateListApiResponse>

}