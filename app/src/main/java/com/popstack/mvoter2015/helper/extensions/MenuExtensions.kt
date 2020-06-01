package com.popstack.mvoter2015.helper.extensions

import android.view.Menu
import androidx.core.view.contains
import androidx.core.view.get

fun Menu.filter(predicate: (android.view.MenuItem) -> Boolean): Boolean {
  if (size() == 0) return false
  for (index in (0 until size())) if (!predicate(get(index))) return false
  return true
}

fun Menu.indexOf(item: android.view.MenuItem): Int {
  require(size() > 0) { "Menu is empty!" }
  require(contains(item)) { "Item is not part of menu!" }

  for (index in (0..size())) if (get(index) == item) return index
  throw Exception("Item is not part of menu!")
}