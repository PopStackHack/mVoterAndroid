package com.popstack.mvoter2015.feature.candidate

import androidx.lifecycle.ViewModel
import com.popstack.mvoter2015.di.viewmodel.ViewModelKey
import com.popstack.mvoter2015.feature.candidate.listing.CandidateListFragment
import com.popstack.mvoter2015.feature.candidate.listing.CandidateListViewModel
import com.popstack.mvoter2015.feature.candidate.listing.lowerhouse.LowerHouseCandidateListFragment
import com.popstack.mvoter2015.feature.candidate.listing.lowerhouse.LowerHouseCandidateListViewModel
import com.popstack.mvoter2015.feature.candidate.listing.regionalhouse.RegionalHouseCandidateListFragment
import com.popstack.mvoter2015.feature.candidate.listing.regionalhouse.RegionalHouseCandidateListViewModel
import com.popstack.mvoter2015.feature.candidate.listing.upperhouse.UpperHouseCandidateListFragment
import com.popstack.mvoter2015.feature.candidate.listing.upperhouse.UpperHouseCandidateListViewModel
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module
internal abstract class CandidateFeatureModule {

  @ContributesAndroidInjector
  abstract fun candidateListingFragment(): CandidateListFragment

  @Binds
  @IntoMap
  @ViewModelKey(CandidateListViewModel::class)
  abstract fun candidateListViewModel(candidateListViewModel: CandidateListViewModel): ViewModel

  @ContributesAndroidInjector
  abstract fun upperHouseCandidateListingFragment(): UpperHouseCandidateListFragment

  @Binds
  @IntoMap
  @ViewModelKey(UpperHouseCandidateListViewModel::class)
  abstract fun upperHouseCandidateListViewModel(upperHouseCandidateListViewModel: UpperHouseCandidateListViewModel): ViewModel

  @ContributesAndroidInjector
  abstract fun lowerHouseCandidateListingFragment(): LowerHouseCandidateListFragment

  @Binds
  @IntoMap
  @ViewModelKey(LowerHouseCandidateListViewModel::class)
  abstract fun lowerHouseCandidateListViewModel(lowerHouseCandidateListViewModel: LowerHouseCandidateListViewModel): ViewModel

  @ContributesAndroidInjector
  abstract fun regionalHouseCandidateListingFragment(): RegionalHouseCandidateListFragment

  @Binds
  @IntoMap
  @ViewModelKey(RegionalHouseCandidateListViewModel::class)
  abstract fun regionalHouseCandidateListViewModel(regionalHouseCandidateListViewModel: RegionalHouseCandidateListViewModel): ViewModel

}