package com.popstack.mvoter2015.data.cache.di

import com.popstack.mvoter2015.data.cache.source.PartyCacheSourceImpl
import com.popstack.mvoter2015.data.common.party.PartyCacheSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent

@InstallIn(ActivityRetainedComponent::class)
@Module
abstract class CacheModule {

  @Binds
  abstract fun partyCacheSource(partyCacheSource: PartyCacheSourceImpl): PartyCacheSource
}