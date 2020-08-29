package com.popstack.mvoter2015.data.network.auth

import com.popstack.mvoter2015.data.network.BuildConfig
import okhttp3.Authenticator
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import javax.inject.Inject

internal class RefreshAuthenticator @Inject constructor(
  private val authTokenStore: AuthTokenStore
) : Authenticator {

  companion object {
    private const val MAX_RESPONSE_COUNT = 3
  }

  private val client = OkHttpClient.Builder().build()

  override fun authenticate(route: Route?, response: Response): Request? {
    if (responseCount(response) >= MAX_RESPONSE_COUNT) {
      //TODO: Broadcast to force quit the app
      return null; // If we've failed 3 times, give up.
    }

    try {
      val appSecret = BuildConfig.APP_SECRET

      val tokenRefreshResponse = TODO("Call refresh endpoint")
      TODO("Store new token")
    } catch (exception: Exception) {
      return null
    }

    TODO("Return with new token")
//    val token = authStore.getAuthToken()
//
//    return@runBlocking response.request.newBuilder()
//      .header("Authorization", "Bearer ${token?.value}")
//      .build()

    //Token
  }

  private fun responseCount(response: Response): Int {
    var result = 1

    var tempResponse = response
    while (tempResponse.priorResponse != null) {
      result++
      tempResponse = tempResponse.priorResponse!!
    }
    return result
  }

}