package com.popstack.mvoter2015.data.android.appupdate

interface SkipVersionCache {

  suspend fun saveSkipVersion(versionCode: Long)

  suspend fun getSkipVersion(): Long?

  suspend fun flush()

}