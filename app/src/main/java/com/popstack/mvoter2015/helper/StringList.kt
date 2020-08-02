package com.popstack.mvoter2015.helper

import java.util.StringJoiner

fun List<String>.format(delimiter: CharSequence): String {
  val stringJoiner = StringJoiner(delimiter)
  this.forEach {
    stringJoiner.add(it)
  }
  return stringJoiner.toString()
}