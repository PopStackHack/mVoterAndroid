package com.popstack.mvoter2015.helper.extensions

import com.google.android.material.tabs.TabLayout

inline fun TabLayout.addOnTabSelectedListener(
  crossinline onTabReselected: ((TabLayout.Tab?) -> Unit) = { _ -> },
  crossinline onTabUnselected: ((TabLayout.Tab?) -> Unit) = { _ -> },
  crossinline onTabSelected: ((TabLayout.Tab?) -> Unit) = { _ -> }
): TabLayout.OnTabSelectedListener {
  val listener = object : TabLayout.OnTabSelectedListener {
    override fun onTabReselected(tab: TabLayout.Tab?) {
      onTabReselected.invoke(tab)
    }

    override fun onTabUnselected(tab: TabLayout.Tab?) {
      onTabUnselected.invoke(tab)
    }

    override fun onTabSelected(tab: TabLayout.Tab?) {
      onTabSelected.invoke(tab)
    }

  }
  this.addOnTabSelectedListener(listener)
  return listener
}

inline fun TabLayout.forEachTab(
  crossinline forEach: (TabLayout.Tab) -> Unit
) {
  (0..tabCount).forEach { index ->
    getTabAt(index)?.let(forEach)
  }

}