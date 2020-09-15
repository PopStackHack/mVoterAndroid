package com.popstack.mvoter2015.feature.votingguide

import androidx.lifecycle.ViewModel
import com.popstack.mvoter2015.di.viewmodel.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module
abstract class VotingGuideFeatureModule {

  @ContributesAndroidInjector
  abstract fun votingGuideController(): VotingGuideController

  @Binds
  @IntoMap
  @ViewModelKey(VotingGuideViewModel::class)
  abstract fun votingGuideViewModel(viewModel: VotingGuideViewModel): ViewModel

}