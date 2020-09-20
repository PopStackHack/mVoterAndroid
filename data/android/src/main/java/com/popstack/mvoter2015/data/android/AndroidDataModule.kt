package com.popstack.mvoter2015.data.android

import android.content.Context
import com.popstack.mvoter2015.data.android.appupdate.AndroidAppUpdateManager
import com.popstack.mvoter2015.data.android.appupdate.NoOpAppUpdateManager
import com.popstack.mvoter2015.data.android.location.LocationProviderModule
import com.popstack.mvoter2015.data.cache.di.CacheModule
import com.popstack.mvoter2015.data.common.appupdate.AppUpdateCacheSource
import com.popstack.mvoter2015.data.common.appupdate.AppUpdateNetworkSource
import com.popstack.mvoter2015.data.common.di.DataModule
import com.popstack.mvoter2015.data.network.di.NetworkModule
import com.popstack.mvoter2015.domain.DispatcherProvider
import com.popstack.mvoter2015.domain.infra.AppUpdateManager
import com.popstack.mvoter2015.domain.infra.AppVersionProvider
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module(includes = [DataModule::class, NetworkModule::class, CacheModule::class, LocationProviderModule::class])
abstract class AndroidDataModule {

  companion object {

    @Provides
    @Singleton
    fun appUpdateManager(
      context: Context,
      appUpdateNetworkSource: AppUpdateNetworkSource,
      appUpdateCacheSource: AppUpdateCacheSource,
      appVersionProvider: AppVersionProvider,
      dispatcherProvider: DispatcherProvider
    ): AppUpdateManager {
      if (BuildConfig.DEBUG) {
        return NoOpAppUpdateManager()
      } else {
        return AndroidAppUpdateManager(
          context,
          appUpdateNetworkSource,
          appUpdateCacheSource,
          appVersionProvider,
          dispatcherProvider
        )
      }
    }
  }

}