package com.popstack.mvoter2015.data.cache

import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager
import com.squareup.sqldelight.android.AndroidSqliteDriver
import com.squareup.sqldelight.db.SqlDriver
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
internal class CacheModule {

  companion object {

//    @Provides
//    @Singleton
//    fun sqlDriver(context: Context): SqlDriver {
//      return AndroidSqliteDriver(MmBaeDb.Schema, context, "hana.db")
//    }
//
//    @Provides
//    @Singleton
//    fun database(sqlDriver: SqlDriver): MmBaeDb {
//      return DbProvider.create(sqlDriver)
//    }

  }

}