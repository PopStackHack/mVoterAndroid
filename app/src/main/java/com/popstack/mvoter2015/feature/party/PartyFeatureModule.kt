package com.popstack.mvoter2015.feature.party

import androidx.lifecycle.ViewModel
import com.popstack.mvoter2015.di.viewmodel.ViewModelKey
import com.popstack.mvoter2015.feature.party.listing.PartyListFragment
import com.popstack.mvoter2015.feature.party.listing.PartyListViewModel
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module
abstract class PartyFeatureModule {

  @ContributesAndroidInjector
  abstract fun partyListFragment(): PartyListFragment

  @Binds
  @IntoMap
  @ViewModelKey(PartyListViewModel::class)
  abstract fun partyListViewModel(partyListViewModel: PartyListViewModel): ViewModel

}