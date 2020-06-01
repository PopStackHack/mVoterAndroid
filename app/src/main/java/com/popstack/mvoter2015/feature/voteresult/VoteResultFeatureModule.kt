package com.popstack.mvoter2015.feature.voteresult

import androidx.lifecycle.ViewModel
import com.popstack.mvoter2015.di.viewmodel.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module
abstract class VoteResultFeatureModule {

  @ContributesAndroidInjector
  abstract fun voteResultFragment(): VoteResultController

  @Binds
  @IntoMap
  @ViewModelKey(VoteResultViewModel::class)
  abstract fun voteResultViewModel(voteResultViewModel: VoteResultViewModel): ViewModel

}