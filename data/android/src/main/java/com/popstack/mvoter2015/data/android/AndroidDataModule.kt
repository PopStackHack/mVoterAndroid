package com.popstack.mvoter2015.data.android

import com.popstack.mvoter2015.data.common.di.DataModule
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent

@InstallIn(ActivityRetainedComponent::class)
@Module(includes = [DataModule::class])
abstract class AndroidDataModule {
  //Do Nothing
}