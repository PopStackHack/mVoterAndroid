package com.popstack.mvoter2015.data.network.di

import android.content.Context
import com.popstack.mvoter2015.data.network.api.MvoterApi
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
abstract class ServiceModule {

  companion object {

    @Provides
    @Singleton
    fun mVoterApi(context: Context): MvoterApi {
      return RetrofitProvider.retrofit(context).create(MvoterApi::class.java)
    }
  }

}