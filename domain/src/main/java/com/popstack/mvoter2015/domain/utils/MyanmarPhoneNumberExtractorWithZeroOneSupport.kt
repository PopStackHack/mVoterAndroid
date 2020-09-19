package com.popstack.mvoter2015.domain.utils

import com.aungkyawpaing.mmphonenumber.normalizer.MyanmarPhoneNumberNormalizer
import com.aungkyawpaing.mmphonenumber.rule.EnglishNumberRule
import com.aungkyawpaing.mmphonenumber.rule.SanitizeRule
import com.aungkyawpaing.mmphonenumber.rule.ZeroNineRule

/**
 * Fork of https://github.com/vincent-paing/myanmar-phonenumber-kt/blob/master/src/main/kotlin/com/aungkyawpaing/mmphonenumber/extract/MyanmarPhoneNumberExtractor.kt
 * Supports 01 number
 */
class MyanmarPhoneNumberExtractorWithZeroOneSupport {

  private val zeroNineNormalizer = MyanmarPhoneNumberNormalizer(
    ruleList = listOf(
      SanitizeRule(),
      EnglishNumberRule(),
      ZeroNineRule()
    )
  )

  private val zeroOneNormalizer = MyanmarPhoneNumberNormalizer(
    ruleList = listOf(
      SanitizeRule(),
      EnglishNumberRule(),
      ZeroOneRule()
    )
  )

  /**
   * Extract list of Burmese phone number from given string
   * @param input: String to be inputted
   * @return empty list if no number is found, list of *normalized* number if it's found.
   */
  fun extract(input: String): List<String> {
    val normalizedInput = if (input.startsWith("၀၉")) {
      zeroNineNormalizer.normalize(input)
    } else if (input.startsWith("၀၁")) {
      zeroOneNormalizer.normalize(input)
    } else {
      zeroNineNormalizer.normalize(input)
    }
    val regex = Regex("((09|\\+?950?9|\\+?95950?9)\\d{7,9})|((01|\\+?950?1|\\+95950?1)\\d{6})")

    return regex.findAll(normalizedInput).map { it.value }.toList()
  }

}