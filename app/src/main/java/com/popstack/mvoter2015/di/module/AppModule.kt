package com.popstack.mvoter2015.di.module

import android.app.Application
import android.content.Context
import coil.ImageLoader
import coil.ImageLoaderBuilder
import com.popstack.mvoter2015.R
import com.popstack.mvoter2015.data.android.AndroidDataModule
import com.popstack.mvoter2015.di.conductor.ConductorInjectionModule
import com.popstack.mvoter2015.di.viewmodel.ViewModelFactoryModule
import com.popstack.mvoter2015.domain.DispatcherProvider
import com.popstack.mvoter2015.feature.HostActivity
import com.popstack.mvoter2015.helper.AndroidDispatcherProvider
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector
import java.time.Clock
import javax.inject.Singleton

@Module(includes = [ViewModelFactoryModule::class, ConductorInjectionModule::class, AndroidDataModule::class])
abstract class AppModule {

  @Binds
  abstract fun dispatcherProvider(dispatcherProvider: AndroidDispatcherProvider): DispatcherProvider

  @ContributesAndroidInjector
  abstract fun hostActivity(): HostActivity

  companion object Provider {

    @Provides
    fun clock(): Clock {
      return Clock.systemDefaultZone()
    }

    @Provides
    fun context(application: Application): Context {
      return application.applicationContext
    }

    @Provides
    @Singleton
    fun imageLoader(context: Context): ImageLoader {
      return ImageLoaderBuilder(context)
        .placeholder(R.drawable.placeholder_rect)
        .build()
    }
  }

}