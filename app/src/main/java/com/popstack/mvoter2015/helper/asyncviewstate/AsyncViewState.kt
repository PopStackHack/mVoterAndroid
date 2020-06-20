package com.popstack.mvoter2015.helper.asyncviewstate

sealed class AsyncViewState<out T> {

  open operator fun invoke(): T? = null

  class Loading<out T> : AsyncViewState<T>()

  data class Success<out T>(val value: T) : AsyncViewState<T>()

  data class Error<out T>(val exception: Throwable, val errorMessage: String) : AsyncViewState<T>()

}