package com.popstack.mvoter2015.feature.home

import androidx.lifecycle.ViewModel
import com.popstack.mvoter2015.di.viewmodel.ViewModelKey
import com.popstack.mvoter2015.feature.candidate.CandidateFeatureModule
import com.popstack.mvoter2015.feature.howtovote.HowToVoteFeatureModule
import com.popstack.mvoter2015.feature.info.InfoFeatureModule
import com.popstack.mvoter2015.feature.party.PartyFeatureModule
import com.popstack.mvoter2015.feature.voteresult.VoteResultFeatureModule
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module(
  includes = [
    CandidateFeatureModule::class,
    PartyFeatureModule::class,
    HowToVoteFeatureModule::class,
    InfoFeatureModule::class,
    VoteResultFeatureModule::class
  ]
)
abstract class HomeFeatureModule {

  @ContributesAndroidInjector
  abstract fun controller(): HomeController

  @Binds
  @IntoMap
  @ViewModelKey(HomeViewModel::class)
  abstract fun homeViewModel(homeViewModel: HomeViewModel): ViewModel

}