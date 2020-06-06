package com.popstack.mvoter2015.data.cache

import android.content.Context
import com.squareup.sqldelight.android.AndroidSqliteDriver
import com.squareup.sqldelight.db.SqlDriver
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class CacheModule {

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