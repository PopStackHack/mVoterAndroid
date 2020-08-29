package com.popstack.mvoter2015.data.network.auth

import com.popstack.mvoter2015.data.network.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response

internal class AuthTokenInterceptor constructor(
  private val authTokenStore: AuthTokenStore
) : Interceptor {

  override fun intercept(chain: Interceptor.Chain): Response {
    //In current stage, API doesn't use refresh token yet
    //So We just use APP_SECRET
    //TODO: This is to be updated later
//    val authToken = authTokenStore.getToken()
    val authToken = BuildConfig.APP_SECRET

    if (authToken != null) {

      val newRequest = chain.request().newBuilder()
        .addHeader("Authorization", "Bearer $authToken")
        .build()

      return chain.proceed(newRequest)
    }
    return chain.proceed(chain.request())
  }

}