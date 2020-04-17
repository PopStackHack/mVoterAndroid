package com.popstack.mvoter2015.data.network

import android.content.Context
import com.aungkyawpaing.monex.MonexInterceptor
import com.popstack.mvoter2015.data.BuildConfig
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
internal class NetworkModule {

  companion object Provider {

    @Provides @Singleton
    fun okHttpClient(
      context: Context
    ): OkHttpClient {

      val okHttpClientBuilder = OkHttpClient.Builder()

      //TODO: Add auth interceptor here later

      if (BuildConfig.DEBUG) {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        okHttpClientBuilder.addNetworkInterceptor(httpLoggingInterceptor)

        val monexInterceptor = MonexInterceptor(context)
        okHttpClientBuilder.addNetworkInterceptor(monexInterceptor)
      }

      val okHttpClient = okHttpClientBuilder
          .build()

      return okHttpClient
    }
  }

  @Provides @Singleton fun retrofit(
    okHttpClient: OkHttpClient
  ): Retrofit {

    val baseUrl = BuildConfig.BASE_URL

    val moshi = Moshi.Builder()
        .build()

    return Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .client(okHttpClient)
        .build()
  }
}