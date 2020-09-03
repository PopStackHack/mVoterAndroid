package com.popstack.mvoter2015.domain.infra

interface AppUpdateManager {

  sealed class UpdateResult {

    data class ForcedUpdate(val updateLink: String) : UpdateResult()

    data class RelaxedUpdate(val updateLink: String) : UpdateResult()

    object NotRequired : UpdateResult()
  }

  suspend fun checkForUpdate(): UpdateResult

}