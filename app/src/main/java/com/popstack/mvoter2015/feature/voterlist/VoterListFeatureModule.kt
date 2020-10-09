package com.popstack.mvoter2015.feature.voterlist

import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class VoterListFeatureModule {

  @ContributesAndroidInjector
  abstract fun voterListController(): VoterListController

}