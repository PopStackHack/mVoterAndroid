package com.popstack.mvoter2015.data.network.di

import android.content.Context
import com.popstack.mvoter2015.data.network.api.MvoterApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton

@InstallIn(ApplicationComponent::class)
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