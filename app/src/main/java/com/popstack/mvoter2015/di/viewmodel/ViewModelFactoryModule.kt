package com.popstack.mvoter2015.di.viewmodel

import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module

/**
 * Created by Vincent on 12/11/18
 */
@Module
abstract class ViewModelFactoryModule {

  @Binds
  abstract fun viewModelFactory(factory: DaggerViewModelFactory): ViewModelProvider.Factory
}