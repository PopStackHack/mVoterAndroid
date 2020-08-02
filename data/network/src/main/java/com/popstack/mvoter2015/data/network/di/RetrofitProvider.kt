package com.popstack.mvoter2015.data.network.di

import android.content.Context
import com.popstack.mvoter2015.data.network.BuildConfig
import com.popstack.mvoter2015.data.network.jsonadapter.LocalDateJsonAdapter
import com.squareup.moshi.Moshi
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

internal object RetrofitProvider {

  private var retrofit: Retrofit? = null

  fun retrofit(okHttpClient: OkHttpClient): Retrofit {
    if (retrofit == null) {
      val baseUrl = BuildConfig.BASE_URL

      val moshi = Moshi.Builder()
        .add(LocalDateJsonAdapter())
        .build()

      retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .client(okHttpClient)
        .build()
    }
    return retrofit!!
  }

  fun retrofit(context: Context): Retrofit {
    return retrofit(OkHttpProvider.okHttpClient(context))
  }
}