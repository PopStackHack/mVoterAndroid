package com.popstack.mvoter2015.feature.candidate

import androidx.lifecycle.ViewModel
import com.popstack.mvoter2015.di.viewmodel.ViewModelKey
import com.popstack.mvoter2015.feature.candidate.detail.CandidateDetailController
import com.popstack.mvoter2015.feature.candidate.detail.CandidateDetailViewModel
import com.popstack.mvoter2015.feature.candidate.listing.CandidateListController
import com.popstack.mvoter2015.feature.candidate.listing.CandidateListViewModel
import com.popstack.mvoter2015.feature.candidate.listing.lowerhouse.LowerHouseCandidateListController
import com.popstack.mvoter2015.feature.candidate.listing.lowerhouse.LowerHouseCandidateListViewModel
import com.popstack.mvoter2015.feature.candidate.listing.regionalhouse.RegionalHouseCandidateListController
import com.popstack.mvoter2015.feature.candidate.listing.regionalhouse.RegionalHouseCandidateListViewModel
import com.popstack.mvoter2015.feature.candidate.listing.upperhouse.UpperHouseCandidateListController
import com.popstack.mvoter2015.feature.candidate.listing.upperhouse.UpperHouseCandidateListViewModel
import com.popstack.mvoter2015.feature.candidate.search.CandidateSearchController
import com.popstack.mvoter2015.feature.candidate.search.CandidateSearchViewModel
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module
abstract class CandidateFeatureModule {

  @ContributesAndroidInjector
  abstract fun candidateListController(): CandidateListController

  @Binds
  @IntoMap
  @ViewModelKey(CandidateListViewModel::class)
  abstract fun candidateListViewModel(viewModel: CandidateListViewModel): ViewModel

  @ContributesAndroidInjector
  abstract fun upperHouseCandidateListController(): UpperHouseCandidateListController

  @Binds
  @IntoMap
  @ViewModelKey(UpperHouseCandidateListViewModel::class)
  abstract fun upperHouseCandidateListViewModel(viewModel: UpperHouseCandidateListViewModel): ViewModel

  @ContributesAndroidInjector
  abstract fun lowerHouseCandidateListController(): LowerHouseCandidateListController

  @Binds
  @IntoMap
  @ViewModelKey(LowerHouseCandidateListViewModel::class)
  abstract fun lowerHouseCandidateListViewModel(viewModel: LowerHouseCandidateListViewModel): ViewModel

  @ContributesAndroidInjector
  abstract fun regionalHouseCandidateListController(): RegionalHouseCandidateListController

  @Binds
  @IntoMap
  @ViewModelKey(RegionalHouseCandidateListViewModel::class)
  abstract fun regionalHouseCandidateListViewModel(viewModel: RegionalHouseCandidateListViewModel): ViewModel

  @ContributesAndroidInjector
  abstract fun candidateDetailController(): CandidateDetailController

  @Binds
  @IntoMap
  @ViewModelKey(CandidateDetailViewModel::class)
  abstract fun candidateDetailViewModel(viewModel: CandidateDetailViewModel): ViewModel

  @ContributesAndroidInjector
  abstract fun candidateSearchController(): CandidateSearchController

  @Binds
  @IntoMap
  @ViewModelKey(CandidateSearchViewModel::class)
  abstract fun candidateSearchViewModel(viewModel: CandidateSearchViewModel): ViewModel

}