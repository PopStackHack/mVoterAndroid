package com.popstack.mvoter2015.feature.howtovote

import androidx.lifecycle.ViewModel
import com.popstack.mvoter2015.di.viewmodel.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module
abstract class HowToVoteFeatureModule {

  @ContributesAndroidInjector
  abstract fun howToVoteController(): HowToVoteController

  @Binds
  @IntoMap
  @ViewModelKey(HowToVoteViewModel::class)
  abstract fun howToVoteViewModel(howToVoteViewModel: HowToVoteViewModel): ViewModel

}