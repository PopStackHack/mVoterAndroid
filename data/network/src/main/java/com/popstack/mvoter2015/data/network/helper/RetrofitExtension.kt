package com.popstack.mvoter2015.data.network.helper

import com.popstack.mvoter2015.data.network.exception.NetworkException
import retrofit2.Call
import retrofit2.Response

fun <T> Call<T>.executeOrThrow(): T {

  val response = this.execute()

  return response.getBodyOrThrowNetworkException()
}

fun <T> Response<T>.getBodyOrThrowNetworkException(): T {

  if (this.isSuccessful.not()) {
    val errorString = this.errorBody()!!
        .byteStream()
        .bufferedReader()
        .use { it.readText() }
    throw NetworkException(errorString, this.code())
  }

  val body = this.body() ?: throw NetworkException()

  return body
}