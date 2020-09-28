package com.popstack.mvoter2015.domain.utils

/**
 * Sanitizer that handles quirkiness of Burmese inputs.
 */
object InputSanitizer {

  fun sanitizeInput(input: String): String {
    return input.replace("\u200B", "") //Bagan keyboard uses zero-width whitespace to do some voodoo stuffs, remove these
  }
}