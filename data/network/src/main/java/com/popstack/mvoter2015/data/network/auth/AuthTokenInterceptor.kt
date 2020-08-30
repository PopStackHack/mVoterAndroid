package com.popstack.mvoter2015.data.network.auth

import okhttp3.Interceptor
import okhttp3.Response

internal class AuthTokenInterceptor constructor(
  private val authTokenStore: AuthTokenStore
) : Interceptor {

  override fun intercept(chain: Interceptor.Chain): Response {
    val authToken = authTokenStore.getToken()

    if (authToken != null) {
      val newRequest = chain.request().newBuilder()
        .addHeader("api-token", authToken)
        .build()
      return chain.proceed(newRequest)
    }

    return chain.proceed(chain.request())
  }

}