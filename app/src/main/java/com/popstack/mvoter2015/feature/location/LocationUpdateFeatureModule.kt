package com.popstack.mvoter2015.feature.location

import androidx.lifecycle.ViewModel
import com.popstack.mvoter2015.di.viewmodel.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module
abstract class LocationUpdateFeatureModule {

  @ContributesAndroidInjector
  abstract fun locationUpdateController(): LocationUpdateController

  @Binds
  @IntoMap
  @ViewModelKey(LocationUpdateViewModel::class)
  abstract fun locationUpdateViewModel(viewModel: LocationUpdateViewModel): ViewModel

  @ContributesAndroidInjector
  abstract fun townshipChooseController(): TownshipChooserController

  @Binds
  @IntoMap
  @ViewModelKey(TownshipChooserViewModel::class)
  abstract fun townshipChooserViewModel(viewModel: TownshipChooserViewModel): ViewModel

  @ContributesAndroidInjector
  abstract fun wardChooserController(): WardChooserController

  @Binds
  @IntoMap
  @ViewModelKey(WardChooserViewModel::class)
  abstract fun wardChooserViewModel(viewModel: WardChooserViewModel): ViewModel

}