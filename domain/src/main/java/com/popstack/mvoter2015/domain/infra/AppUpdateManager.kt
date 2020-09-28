package com.popstack.mvoter2015.domain.infra

interface AppUpdateManager {

  sealed class UpdateResult {

    data class ForcedUpdate(val updateLink: String) : UpdateResult()

    data class RelaxedUpdate(val updateLink: String, val isSkipped: Boolean) : UpdateResult()

    object NotRequired : UpdateResult()
  }

  suspend fun checkForUpdate(): UpdateResult

  suspend fun skipCurrentUpdate()

}