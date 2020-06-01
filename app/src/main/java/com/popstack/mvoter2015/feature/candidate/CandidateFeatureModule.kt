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
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module
internal abstract class CandidateFeatureModule {

  @ContributesAndroidInjector
  abstract fun candidateListController(): CandidateListController

  @Binds
  @IntoMap
  @ViewModelKey(CandidateListViewModel::class)
  abstract fun candidateListViewModel(candidateListViewModel: CandidateListViewModel): ViewModel

  @ContributesAndroidInjector
  abstract fun upperHouseCandidateListingController(): UpperHouseCandidateListController

  @Binds
  @IntoMap
  @ViewModelKey(UpperHouseCandidateListViewModel::class)
  abstract fun upperHouseCandidateListViewModel(upperHouseCandidateListViewModel: UpperHouseCandidateListViewModel): ViewModel

  @ContributesAndroidInjector
  abstract fun lowerHouseCandidateListingController(): LowerHouseCandidateListController

  @Binds
  @IntoMap
  @ViewModelKey(LowerHouseCandidateListViewModel::class)
  abstract fun lowerHouseCandidateListViewModel(lowerHouseCandidateListViewModel: LowerHouseCandidateListViewModel): ViewModel

  @ContributesAndroidInjector
  abstract fun regionalHouseCandidateListingController(): RegionalHouseCandidateListController

  @Binds
  @IntoMap
  @ViewModelKey(RegionalHouseCandidateListViewModel::class)
  abstract fun regionalHouseCandidateListViewModel(regionalHouseCandidateListViewModel: RegionalHouseCandidateListViewModel): ViewModel

  @ContributesAndroidInjector
  abstract fun candidateDetailController(): CandidateDetailController

  @Binds
  @IntoMap
  @ViewModelKey(CandidateDetailViewModel::class)
  abstract fun candidateDetailViewModel(candidateDetailViewModel: CandidateDetailViewModel): ViewModel

}