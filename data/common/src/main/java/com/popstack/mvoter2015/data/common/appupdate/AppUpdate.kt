package com.popstack.mvoter2015.data.common.appupdate

data class AppUpdate(
  val latestVersionCode: Int,
  val requireForcedUpdate: Boolean,
  val playStoreLink: String,
  val selfHostedLink: String
)