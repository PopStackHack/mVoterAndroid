package com.popstack.mvoter2015.feature.about

import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class AboutFeatureModule {

  @ContributesAndroidInjector
  abstract fun aboutController(): AboutController

}