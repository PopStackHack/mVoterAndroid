package com.popstack.mvoter2015.domain.utils

import com.aungkyawpaing.mmphonenumber.normalizer.Rule

class ZeroOneRule : Rule {

  private val possibleCases = Regex("(01-)|(\\+951)|(01\\s)|(951)|(01\\.)")

  override fun convert(input: String): String {

    if (possibleCases.containsMatchIn(input)) {
      return input.replaceFirst(possibleCases, "01")
    }

    return input
  }
}