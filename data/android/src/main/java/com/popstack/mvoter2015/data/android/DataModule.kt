package com.popstack.mvoter2015.data.android

import com.popstack.mvoter2015.data.cache.CacheModule
import com.popstack.mvoter2015.data.network.di.NetworkModule
import dagger.Module

@Module(includes = [CacheModule::class, NetworkModule::class])
abstract class DataModule {
  //TODO: Add repository provider here
}