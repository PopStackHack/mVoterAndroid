package com.popstack.mvoter2015.feature.party

import androidx.lifecycle.ViewModel
import com.popstack.mvoter2015.di.viewmodel.ViewModelKey
import com.popstack.mvoter2015.feature.party.detail.PartyDetailController
import com.popstack.mvoter2015.feature.party.detail.PartyDetailViewModel
import com.popstack.mvoter2015.feature.party.listing.PartyListController
import com.popstack.mvoter2015.feature.party.listing.PartyListViewModel
import com.popstack.mvoter2015.feature.party.search.PartySearchController
import com.popstack.mvoter2015.feature.party.search.PartySearchViewModel
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module
abstract class PartyFeatureModule {

  @ContributesAndroidInjector
  abstract fun partyListController(): PartyListController

  @Binds
  @IntoMap
  @ViewModelKey(PartyListViewModel::class)
  abstract fun partyListViewModel(viewModel: PartyListViewModel): ViewModel

  @ContributesAndroidInjector
  abstract fun partyDetailController(): PartyDetailController

  @Binds
  @IntoMap
  @ViewModelKey(PartyDetailViewModel::class)
  abstract fun partyDetailViewModel(viewModel: PartyDetailViewModel): ViewModel

  @ContributesAndroidInjector
  abstract fun partySearchController(): PartySearchController

  @Binds
  @IntoMap
  @ViewModelKey(PartySearchViewModel::class)
  abstract fun partySearchViewModel(viewModel: PartySearchViewModel): ViewModel

}