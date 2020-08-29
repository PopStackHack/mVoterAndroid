package com.popstack.mvoter2015.data.network.auth

import android.content.Context
import androidx.core.content.edit
import javax.inject.Inject

internal class AuthTokenStoreImpl @Inject constructor(
  context: Context
) : AuthTokenStore {

  private val sharedPreferences = context.getSharedPreferences("auth", Context.MODE_PRIVATE)

  companion object {
    private const val KEY_TOKEN = "auth_token"
  }

  override fun storeToken(token: String) {
    sharedPreferences.edit {
      putString(KEY_TOKEN, token)
    }
  }

  override fun getToken(): String? {
    return sharedPreferences.getString(KEY_TOKEN, null)
  }

  override fun flush() {
    sharedPreferences.edit {
      clear()
    }
  }

}