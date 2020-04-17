package com.popstack.mvoter2015.data.network.helper

import com.popstack.mvoter2015.data.network.exception.NetworkException
import okhttp3.Call
import okhttp3.Response
import okhttp3.ResponseBody

fun Call.executeOrThrow(): ResponseBody {

  val response = this.execute()

  return response.getBodyOrThrowNetworkException()
}

fun Response.getBodyOrThrowNetworkException(): ResponseBody {

  if (this.isSuccessful.not()) {
    val errorString = this.body!!.byteStream()
      .bufferedReader()
      .use { it.readText() }
    throw NetworkException(errorString, this.code)
  }

  val body = this.body ?: throw NetworkException()

  return body
}