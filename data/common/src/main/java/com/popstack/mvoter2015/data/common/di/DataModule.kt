package com.popstack.mvoter2015.data.common.di

import com.popstack.mvoter2015.data.common.faq.FaqRepositoryImpl
import com.popstack.mvoter2015.data.common.party.PartyRepositoryImpl
import com.popstack.mvoter2015.domain.faq.FaqRepository
import com.popstack.mvoter2015.domain.party.usecase.PartyRepository
import dagger.Binds
import dagger.Module

@Module
abstract class DataModule {

  @Binds
  abstract fun partyRepository(partyRepository: PartyRepositoryImpl): PartyRepository

  @Binds
  abstract fun faqRepository(faqRepository: FaqRepositoryImpl): FaqRepository
}