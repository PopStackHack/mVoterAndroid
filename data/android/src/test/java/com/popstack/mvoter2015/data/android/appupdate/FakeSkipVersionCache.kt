package com.popstack.mvoter2015.data.android.appupdate

import javax.inject.Inject

class FakeSkipVersionCache @Inject constructor() : SkipVersionCache {

  var skippedVersionCode: Long? = null

  override suspend fun saveSkipVersion(versionCode: Long) {
    skippedVersionCode = versionCode
  }

  override suspend fun getSkipVersion(): Long? {
    return skippedVersionCode
  }

  override suspend fun flush() {
    skippedVersionCode = null
  }

}