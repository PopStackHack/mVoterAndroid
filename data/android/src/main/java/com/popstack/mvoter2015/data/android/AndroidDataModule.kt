package com.popstack.mvoter2015.data.android

import com.popstack.mvoter2015.data.android.location.LocationProviderModule
import com.popstack.mvoter2015.data.cache.di.CacheModule
import com.popstack.mvoter2015.data.common.di.DataModule
import com.popstack.mvoter2015.data.network.di.NetworkModule
import dagger.Module

@Module(includes = [DataModule::class, NetworkModule::class, CacheModule::class, LocationProviderModule::class])
abstract class AndroidDataModule