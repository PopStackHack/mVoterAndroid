package com.popstack.mvoter2015.feature.candidate

import androidx.lifecycle.ViewModel
import com.popstack.mvoter2015.di.viewmodel.ViewModelKey
import com.popstack.mvoter2015.feature.candidate.listing.CandidateListFragment
import com.popstack.mvoter2015.feature.candidate.listing.CandidateListViewModel
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module
abstract class CandidateFeatureModule {

    @ContributesAndroidInjector
    abstract fun candidateListingFragment(): CandidateListFragment

    @Binds
    @IntoMap
    @ViewModelKey(CandidateListViewModel::class)
    abstract fun candidateListViewModel(candidateListViewModel: CandidateListViewModel): ViewModel

}