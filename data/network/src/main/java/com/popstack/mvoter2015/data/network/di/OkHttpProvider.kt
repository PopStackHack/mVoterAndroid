package com.popstack.mvoter2015.data.network.di

import android.content.Context
import com.aungkyawpaing.monex.MonexInterceptor
import com.popstack.mvoter2015.data.network.BuildConfig
import com.popstack.mvoter2015.data.network.auth.AuthTokenInterceptor
import com.popstack.mvoter2015.data.network.auth.AuthTokenStoreImpl
import com.popstack.mvoter2015.data.network.auth.RefreshAuthenticator
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor

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

      okHttpClientBuilder
        .addInterceptor(AuthTokenInterceptor(authTokenStore))
        .authenticator(RefreshAuthenticator(authTokenStore))

      okHttpClient = okHttpClientBuilder.build()
    }

    return okHttpClient!!

  }
}