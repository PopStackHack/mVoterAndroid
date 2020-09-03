package com.popstack.mvoter2015.data.cache.appupdate

import com.popstack.mvoter2015.data.common.appupdate.AppUpdate
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class AppUpdateCacheEntity(
  @Json(name = "latest_version_code") val latestVersionCode: Int,
  @Json(name = "require_forced_update") val requireForcedUpdate: Boolean,
  @Json(name = "play_store_link") val playStoreLink: String,
  @Json(name = "download_link") val selfHostedLink: String
) {

  fun toAppUpdate(): AppUpdate {
    return AppUpdate(
      latestVersionCode = this.latestVersionCode,
      requireForcedUpdate = this.requireForcedUpdate,
      playStoreLink = this.playStoreLink,
      selfHostedLink = this.selfHostedLink
    )
  }
}

internal fun AppUpdate.toCacheEntity(): AppUpdateCacheEntity {
  return AppUpdateCacheEntity(
    latestVersionCode = this.latestVersionCode,
    requireForcedUpdate = this.requireForcedUpdate,
    playStoreLink = this.playStoreLink,
    selfHostedLink = this.selfHostedLink
  )
}