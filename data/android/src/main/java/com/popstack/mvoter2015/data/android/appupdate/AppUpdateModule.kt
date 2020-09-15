package com.popstack.mvoter2015.data.android.appupdate

import com.popstack.mvoter2015.domain.infra.AppVersionProvider
import dagger.Binds
import dagger.Module

@Module
abstract class AppUpdateModule {

  @Binds
  abstract fun appUpdateProvider(appVersionProvider: AppVersionProviderImpl): AppVersionProvider

}