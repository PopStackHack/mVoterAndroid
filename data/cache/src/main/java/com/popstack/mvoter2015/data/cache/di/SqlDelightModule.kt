package com.popstack.mvoter2015.data.cache.di

import android.content.Context
import com.popstack.mvoter2015.data.cache.DbProvider
import com.popstack.mvoter2015.data.cache.MVoterDb
import com.squareup.sqldelight.android.AndroidSqliteDriver
import com.squareup.sqldelight.db.SqlDriver
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton

@InstallIn(ApplicationComponent::class)
@Module
class SqlDelightModule {

  companion object {

    @Provides
    @Singleton
    fun sqlDriver(context: Context): SqlDriver {
      return AndroidSqliteDriver(MVoterDb.Schema, context, "mvoter.db")
    }

    @Provides
    @Singleton
    fun database(sqlDriver: SqlDriver): MVoterDb {
      return DbProvider.create(sqlDriver)
    }

  }
}