package com.popstack.mvoter2015.data.network.cache

import okhttp3.CacheControl
import okhttp3.Interceptor
import okhttp3.Response
import java.time.Duration
import java.util.concurrent.TimeUnit

class AutoCachingOnlineInterceptor : Interceptor {

  companion object {
    private val MAX_AGE = Duration.ofDays(1).toMillis()
  }

  override fun intercept(chain: Interceptor.Chain): Response {

    val response = chain.proceed(chain.request())
    return response
      .newBuilder()
      .header("Cache-Control", "public, max-age=$MAX_AGE")
      .removeHeader("Pragma")
      .build()
  }

}


class AutoCachingOfflineInterceptor : Interceptor {

  companion object {
    private val MAX_AGE = Duration.ofDays(1).toMillis()
  }

  override fun intercept(chain: Interceptor.Chain): Response {

    val request = chain.request()
      .newBuilder()
      .cacheControl(CacheControl.Builder()
        .maxAge(1, TimeUnit.DAYS)
        .build())
      .build()

    return chain.proceed(request)
  }

}
