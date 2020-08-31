package com.popstack.mvoter2015.feature.news

import androidx.lifecycle.ViewModel
import com.popstack.mvoter2015.di.viewmodel.ViewModelKey
import com.popstack.mvoter2015.feature.news.search.NewsSearchController
import com.popstack.mvoter2015.feature.news.search.NewsSearchViewModel
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module
abstract class NewsFeatureModule {

  @ContributesAndroidInjector
  abstract fun newsController(): NewsController

  @Binds
  @IntoMap
  @ViewModelKey(NewsViewModel::class)
  abstract fun newsViewModel(viewModel: NewsViewModel): ViewModel

  @ContributesAndroidInjector
  abstract fun newsSearchController(): NewsSearchController

  @Binds
  @IntoMap
  @ViewModelKey(NewsSearchViewModel::class)
  abstract fun newsSearchViewModel(viewModel: NewsSearchViewModel): ViewModel

}