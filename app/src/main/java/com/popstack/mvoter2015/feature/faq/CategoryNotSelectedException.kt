package com.popstack.mvoter2015.feature.faq

class CategoryNotSelectedException : Throwable() {

  override fun getLocalizedMessage(): String? {
    return "Please select category"
  }

}