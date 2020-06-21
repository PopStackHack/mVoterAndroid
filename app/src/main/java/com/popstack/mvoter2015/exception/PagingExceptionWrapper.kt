package com.popstack.mvoter2015.exception

data class PagingExceptionWrapper(
  val errorMessage: String,
  val originalException: Throwable
) : Throwable() {

  override val message: String?
    get() = errorMessage

  override val cause: Throwable?
    get() = originalException

}