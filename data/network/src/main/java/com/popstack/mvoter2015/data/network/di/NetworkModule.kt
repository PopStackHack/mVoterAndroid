package com.popstack.mvoter2015.data.network.di

import com.popstack.mvoter2015.data.common.faq.FaqNetworkSource
import com.popstack.mvoter2015.data.common.news.NewsNetworkSource
import com.popstack.mvoter2015.data.common.party.PartyNetworkSource
import com.popstack.mvoter2015.data.network.source.FaqNetworkSourceImpl
import com.popstack.mvoter2015.data.network.source.NewsNetworkSourceImpl
import com.popstack.mvoter2015.data.network.source.PartyNetworkSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent

@Module
@InstallIn(ActivityRetainedComponent::class)
abstract class NetworkModule {

  @Binds
  abstract fun partyNetworkSource(partyNetworkSource: PartyNetworkSourceImpl): PartyNetworkSource

  @Binds
  abstract fun faqNetworkSource(faqNetworkSource: FaqNetworkSourceImpl): FaqNetworkSource

  @Binds
  abstract fun newsNetworkSource(newsNetworkSource: NewsNetworkSourceImpl): NewsNetworkSource
}