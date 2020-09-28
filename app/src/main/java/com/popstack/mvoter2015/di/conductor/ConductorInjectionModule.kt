package com.popstack.mvoter2015.di.conductor

import com.bluelinelabs.conductor.Controller
import dagger.Module
import dagger.android.AndroidInjectionModule
import dagger.internal.Beta
import dagger.internal.Factory
import dagger.multibindings.Multibinds

@Beta
@Module(includes = [AndroidInjectionModule::class])
abstract class ConductorInjectionModule {

  @Multibinds
  abstract fun controllerInjectorFactories(): Map<Class<out Controller?>?, Factory<out Controller?>?>?

  @Multibinds
  abstract fun controllerInjectorFactoriesWithStringKeys(): Map<String?, Factory<out Controller?>?>?
}