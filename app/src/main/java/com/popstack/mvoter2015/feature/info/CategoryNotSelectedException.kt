package com.popstack.mvoter2015.feature.info

class CategoryNotSelectedException : Throwable() {

  override fun getLocalizedMessage(): String? {
    return "Please select category"
  }

}