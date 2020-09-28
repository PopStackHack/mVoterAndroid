package com.popstack.mvoter2015.data.common.appupdate

data class AppUpdate(
  val latestVersionCode: Long,
  val requireForcedUpdate: Boolean,
  val playStoreLink: String,
  val selfHostedLink: String
)