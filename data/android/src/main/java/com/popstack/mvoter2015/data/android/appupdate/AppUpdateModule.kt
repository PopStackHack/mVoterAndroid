package com.popstack.mvoter2015.data.android.appupdate

import android.content.Context
import com.popstack.mvoter2015.data.android.BuildConfig
import com.popstack.mvoter2015.data.common.appupdate.AppUpdateCacheSource
import com.popstack.mvoter2015.data.common.appupdate.AppUpdateNetworkSource
import com.popstack.mvoter2015.domain.DispatcherProvider
import com.popstack.mvoter2015.domain.infra.AppUpdateManager
import com.popstack.mvoter2015.domain.infra.AppVersionProvider
import dagger.Binds
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
abstract class AppUpdateModule {

  @Binds
  abstract fun appUpdateProvider(appVersionProvider: AppVersionProviderImpl): AppVersionProvider

  @Binds
  abstract fun skipVersionCache(skipVersionCache: SkipVersionCacheImpl): SkipVersionCache

  companion object {

    @Provides
    @Singleton
    fun appUpdateManager(
      context: Context,
      appUpdateNetworkSource: AppUpdateNetworkSource,
      appUpdateCacheSource: AppUpdateCacheSource,
      skipVersionCache: SkipVersionCache,
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
          skipVersionCache,
          appVersionProvider,
          dispatcherProvider
        )
      }
    }
  }

}