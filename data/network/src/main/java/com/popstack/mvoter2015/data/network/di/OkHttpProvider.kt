package com.popstack.mvoter2015.data.network.di

import android.content.Context
import com.aungkyawpaing.monex.MonexInterceptor
import com.popstack.mvoter2015.data.network.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor

internal object OkHttpProvider {

  private var okHttpClient: OkHttpClient? = null

  fun okHttpClient(context: Context): OkHttpClient {

    if (okHttpClient == null) {
      val okHttpClientBuilder = OkHttpClient.Builder()

      if (BuildConfig.DEBUG) {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        okHttpClientBuilder.addNetworkInterceptor(httpLoggingInterceptor)

        val monexInterceptor = MonexInterceptor(context)
        okHttpClientBuilder.addNetworkInterceptor(monexInterceptor)
      }

      okHttpClient = okHttpClientBuilder
        .build()
    }

    return okHttpClient!!

  }
}