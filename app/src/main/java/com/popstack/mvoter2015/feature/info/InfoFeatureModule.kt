package com.popstack.mvoter2015.feature.info

import androidx.lifecycle.ViewModel
import com.popstack.mvoter2015.di.viewmodel.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module
abstract class InfoFeatureModule {

  @ContributesAndroidInjector
  abstract fun infoFragment(): InfoController

  @Binds
  @IntoMap
  @ViewModelKey(InfoViewModel::class)
  abstract fun infoViewModel(infoViewModel: InfoViewModel): ViewModel

}