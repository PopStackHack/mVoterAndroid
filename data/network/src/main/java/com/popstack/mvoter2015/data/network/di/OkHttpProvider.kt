package com.popstack.mvoter2015.data.network.di

import android.content.Context
import com.aungkyawpaing.monex.MonexInterceptor
import com.popstack.mvoter2015.data.network.BuildConfig
import com.popstack.mvoter2015.data.network.auth.AuthTokenInterceptor
import com.popstack.mvoter2015.data.network.auth.AuthTokenStoreImpl
import com.popstack.mvoter2015.data.network.auth.RefreshAuthenticator
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.io.File

internal object OkHttpProvider {

  private var okHttpClient: OkHttpClient? = null

  fun okHttpClient(context: Context): OkHttpClient {

    if (okHttpClient == null) {
      val okHttpClientBuilder = OkHttpClient.Builder()

      val authTokenStore = AuthTokenStoreImpl(context)

      if (BuildConfig.DEBUG) {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        okHttpClientBuilder.addNetworkInterceptor(httpLoggingInterceptor)

        val monexInterceptor = MonexInterceptor(context)
        okHttpClientBuilder.addNetworkInterceptor(monexInterceptor)
      }

      val cache = Cache(
        directory = File(context.cacheDir, "http_cache"),
        maxSize = 50L * 1024L * 1024L // 50 MiB
      )

      okHttpClientBuilder
//        .cache(cache)
        .addInterceptor(AuthTokenInterceptor(authTokenStore))
        .authenticator(RefreshAuthenticator(authTokenStore))

      okHttpClient = okHttpClientBuilder.build()
    }

    return okHttpClient!!

  }
}