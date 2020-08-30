package com.popstack.mvoter2015.data.network.auth

import com.popstack.mvoter2015.data.network.BuildConfig
import com.popstack.mvoter2015.data.network.helper.executeOrThrow
import okhttp3.Authenticator
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response
import okhttp3.Route
import org.json.JSONObject
import java.time.Duration
import javax.inject.Inject

internal class RefreshAuthenticator @Inject constructor(
  private val authTokenStore: AuthTokenStore
) : Authenticator {

  companion object {
    private val MINUTES_TO_EXPIRE = Duration.ofDays(1).toMinutes()
    private const val MAX_RESPONSE_COUNT = 3
  }

  private val client = OkHttpClient.Builder().build()

  override fun authenticate(route: Route?, response: Response): Request? {
    if (responseCount(response) >= MAX_RESPONSE_COUNT) {
      //TODO: Broadcast to force quit the app
      return null; // If we've failed 3 times, give up.
    }

    try {
      val apiSecret = BuildConfig.APP_SECRET

      val authCall = client.newCall(
        Request.Builder()
          .url("${BuildConfig.BASE_URL}authenticate")
          .post("".toRequestBody())
          .header("api-key", apiSecret)
          .build()
      )

      val responseBody = authCall.executeOrThrow()
      val responseJson = JSONObject(responseBody.string())
      val token = responseJson.getString("token")

      authTokenStore.storeToken(token)
    } catch (exception: Exception) {
      //Fail to auth just return original request to try again
      return response.request.newBuilder().build()
    }

    //Return original request to try again if there's no token
    val token = authTokenStore.getToken() ?: return response.request.newBuilder().build()

    return response.request.newBuilder()
      .header("api-token", token)
      .build()
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