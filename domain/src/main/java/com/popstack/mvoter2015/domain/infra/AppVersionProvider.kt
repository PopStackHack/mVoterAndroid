package com.popstack.mvoter2015.domain.infra

interface AppVersionProvider {

  fun versionCode(): Long

  fun versionName(): String

}