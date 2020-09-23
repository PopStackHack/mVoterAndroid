package com.popstack.mvoter2015.data.android.appupdate

import android.content.Context
import androidx.datastore.preferences.createDataStore
import androidx.datastore.preferences.edit
import androidx.datastore.preferences.preferencesKey
import androidx.datastore.preferences.remove
import kotlinx.coroutines.flow.firstOrNull
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SkipVersionCacheImpl @Inject constructor(
  context: Context
) : SkipVersionCache {

  companion object {
    private val KEY_SKIPPED_VERSION = preferencesKey<Long>("skipped_version")
  }

  private val skipVersionPref = context.createDataStore("skip_version_code")

  override suspend fun saveSkipVersion(versionCode: Long) {
    skipVersionPref.edit {
      it[KEY_SKIPPED_VERSION] = versionCode
    }
  }

  override suspend fun getSkipVersion(): Long? {
    return skipVersionPref.data.firstOrNull()?.get(KEY_SKIPPED_VERSION)
  }

  override suspend fun flush() {
    skipVersionPref.edit {
      it.remove(KEY_SKIPPED_VERSION)
    }
  }

}