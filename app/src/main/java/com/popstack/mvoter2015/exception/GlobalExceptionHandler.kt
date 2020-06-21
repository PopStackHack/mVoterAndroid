package com.popstack.mvoter2015.exception

import android.content.Context
import com.popstack.mvoter2015.R
import com.popstack.mvoter2015.data.network.exception.NetworkException
import org.json.JSONObject
import timber.log.Timber
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.inject.Inject

class GlobalExceptionHandler @Inject constructor(
  private val context: Context
) {

  companion object {
    private const val ERROR_CODE_400 = 400
    private const val ERROR_CODE_401 = 401
    private const val ERROR_CODE_422 = 422
    private const val ERROR_CODE_403 = 403
    private const val ERROR_CODE_404 = 404
    private const val ERROR_CODE_500 = 404
  }

  fun getMessageForUser(throwable: Throwable): String {
    return when (throwable) {
      is UnknownHostException -> context.getString(R.string.error_no_internet)
      is SocketTimeoutException -> context.getString(R.string.error_socket_timeout)
      is ConnectException -> context.getString(R.string.error_no_internet)
      is NetworkException -> parseNetworkError(throwable)
      else -> context.getString(R.string.error_generic)
    }
  }

  private fun parseNetworkError(exception: NetworkException): String {
    when (exception.errorCode) {
      ERROR_CODE_400 -> return exception.errorBody?.let { parseMessageFromErrorBody(it) }
        ?: context.getString(
          R.string.error_generic
        )
      ERROR_CODE_401 -> return exception.errorBody?.let { parseMessageFromErrorBody(it) }
        ?: context.getString(
          R.string.error_generic
        )
      ERROR_CODE_422 -> return exception.errorBody?.let { parseMessageFromErrorBody(it) }
        ?: context.getString(
          R.string.error_generic
        )
      ERROR_CODE_403 -> return exception.errorBody?.let { parseMessageFromErrorBody(it) }
        ?: context.getString(
          R.string.error_generic
        )
      ERROR_CODE_404 -> return context.getString(R.string.error_server_404)
      ERROR_CODE_500 -> return context.getString(R.string.error_server_500)
    }

    return context.getString(R.string.error_generic)
  }

  private fun parseMessageFromErrorBody(errorBody: String): String {

    if (errorBody == null) {
      return context.getString(R.string.error_generic)
    }

    Timber.i("error body in string : $errorBody")

    try {
      val errorBodyJson = JSONObject(errorBody)

      val errorMessage = errorBodyJson.getString("message")

      return errorMessage
    } catch (exception: Exception) {
      Timber.e(exception)
    }

    return context.getString(R.string.error_generic)
  }
}