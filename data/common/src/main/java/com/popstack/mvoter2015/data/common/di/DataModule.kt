package com.popstack.mvoter2015.data.common.di

import com.popstack.mvoter2015.data.common.party.PartyRepositoryImpl
import com.popstack.mvoter2015.domain.party.usecase.PartyRepository
import dagger.Binds
import dagger.Module

@Module
abstract class DataModule {

  @Binds
  abstract fun repository(partyRepository: PartyRepositoryImpl): PartyRepository
}