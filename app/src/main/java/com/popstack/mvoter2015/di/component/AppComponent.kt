package com.popstack.mvoter2015.di.component

import android.app.Application
import com.popstack.mvoter2015.MVoterApp
import com.popstack.mvoter2015.di.module.AppModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

/**
 * Created by Vincent on 2019-06-20
 */
@Singleton
@Component(
    modules = [AppModule::class,
      AndroidInjectionModule::class,
      AndroidSupportInjectionModule::class]
)
interface AppComponent {

  @Component.Builder
  interface Builder {
    @BindsInstance
    fun application(application: Application): Builder

    fun build(): AppComponent
  }

  fun inject(application: MVoterApp)

}