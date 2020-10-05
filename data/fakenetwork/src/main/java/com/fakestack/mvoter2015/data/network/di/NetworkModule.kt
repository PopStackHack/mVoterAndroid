package com.popstack.mvoter2015.data.network.di

import com.fakestack.mvoter2015.data.network.FakeAppUpdateNetworkDataSource
import com.fakestack.mvoter2015.data.network.FakeCandidateNetworkDataSource
import com.fakestack.mvoter2015.data.network.FakeFaqNetworkDataSource
import com.fakestack.mvoter2015.data.network.FakeLocationNetworkDataSource
import com.fakestack.mvoter2015.data.network.FakeNewsNetworkDataSource
import com.fakestack.mvoter2015.data.network.FakePartyNetworkDataSource
import com.popstack.mvoter2015.data.common.appupdate.AppUpdateNetworkSource
import com.popstack.mvoter2015.data.common.candidate.CandidateNetworkSource
import com.popstack.mvoter2015.data.common.faq.FaqNetworkSource
import com.popstack.mvoter2015.data.common.location.LocationNetworkSource
import com.popstack.mvoter2015.data.common.news.NewsNetworkSource
import com.popstack.mvoter2015.data.common.party.PartyNetworkSource
import dagger.Binds
import dagger.Module

@Module
abstract class NetworkModule {

  @Binds
  abstract fun appUpdateNetworkSource(appUpdateNetworkSource: FakeAppUpdateNetworkDataSource): AppUpdateNetworkSource

  @Binds
  abstract fun partyNetworkSource(partyNetworkSource: FakePartyNetworkDataSource): PartyNetworkSource

  @Binds
  abstract fun faqNetworkSource(faqNetworkSource: FakeFaqNetworkDataSource): FaqNetworkSource

  @Binds
  abstract fun newsNetworkSource(newsNetworkSource: FakeNewsNetworkDataSource): NewsNetworkSource

  @Binds
  abstract fun candidateNetworkSource(candidateNetworkSourceImpl: FakeCandidateNetworkDataSource): CandidateNetworkSource

  @Binds
  abstract fun locationNetworkSource(locationNetworkSource: FakeLocationNetworkDataSource): LocationNetworkSource
}