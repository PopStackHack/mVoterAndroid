package com.popstack.mvoter2015.data.network.auth

internal interface AuthTokenStore {

  fun storeToken(token: String)

  fun getToken(): String?

  fun flush()

}