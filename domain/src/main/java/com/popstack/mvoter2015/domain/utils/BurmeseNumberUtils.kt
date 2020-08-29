package com.popstack.mvoter2015.domain.utils


object BurmeseNumberUtils {

  /**
   * Transform English number to Burmese number (e.g "30" -> "၃၀");
   * This does not validate before converting, use [isBurmeseNumber] to check if it's valid Burmese number
   *
   * @param burmeseNumberCharacter A Burmese number character, ၀ - ၉.
   * @return English version of number string
   */
  fun convertBurmeseToEnglishNumber(burmeseNumberCharacter: Char): Char {
    return (burmeseNumberCharacter.toInt() - 4112).toChar()
  }

  fun convertBurmeseToEnglishNumber(burmeseNumber: CharSequence): CharSequence {
    val stringBuilder = StringBuilder()
    burmeseNumber.forEach {
      if (isBurmeseNumber(it)) {
        stringBuilder.append(convertBurmeseToEnglishNumber(it))
      } else {
        stringBuilder.append(it)
      }
    }
    return stringBuilder
  }

  /**
   * Transform Burmese number to English number (e.g "30" -> "၃၀");
   * This does not validate before converting, use [isEnglishNumber] to check if it's valid English number
   *
   * @param burmeseNumberCharacter A English number character, 0 - 9.
   * @return Burmese version of number string
   */
  fun convertEnglishToBurmeseNumber(burmeseNumberCharacter: Char): Char {
    return (burmeseNumberCharacter.toInt() + 4112).toChar()
  }

  fun convertEnglishToBurmeseNumber(englishNumber: CharSequence): CharSequence {
    val stringBuilder = StringBuilder()
    englishNumber.forEach {
      if (isEnglishNumber(it)) {
        stringBuilder.append(convertEnglishToBurmeseNumber(it))
      } else {
        stringBuilder.append(it)
      }
    }
    return stringBuilder
  }

  /**
   * Check whether a [character] is a Burmese number, ၀ - ၉
   * @param character any [Char]
   * @return true if Burmese number, false if it's not Burmese
   */
  fun isBurmeseNumber(character: Char): Boolean {
    return character in '၀'..'၉'
  }

  /**
   * Check whether a [character] is a English number, 0 - 9
   * @param character any [Char]
   * @return true if English number, false if it's not English
   */
  fun isEnglishNumber(character: Char): Boolean {
    return character in '0'..'9'
  }

  /**
   * Check whether a [string] is a Burmese number, ၀ - ၉
   * @param string any [String]
   * @return true if Burmese number, false if it's not Burmese
   */
  fun isBurmeseNumber(string: String): Boolean {
    return string.map { isBurmeseNumber(it) }.contains(false).not()
  }

  /**
   * Check whether a [string] is a English number, 0 - 9
   * @param string any [String]
   * @return true if English number, false if it's not English
   */
  fun isEnglishNumber(string: String): Boolean {
    return string.map { isEnglishNumber(it) }.contains(false).not()
  }
}

fun String.convertToBurmeseNumber(): String {
  return BurmeseNumberUtils.convertEnglishToBurmeseNumber(this).toString()
}

fun String.convertToEnglishNumber(): String {
  return BurmeseNumberUtils.convertBurmeseToEnglishNumber(this).toString()
}