package com.popstack.mvoter2015.helper

import android.view.View
import androidx.core.view.isVisible
import androidx.core.widget.ContentLoadingProgressBar
import androidx.lifecycle.findViewTreeLifecycleOwner
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * A Debounce method to handle view visibility changes
 * This can be used to prevent screen flashes when visibility of view changes
 * One such case could be to show a progressbar when the loading of the data is too fast
 * This try to mimic the reverse behavior of [ContentLoadingProgressBar] in short
 *
 * @param view Reference to View
 * @param delayTimeInMili Debounce timer, default is 500ms
 */
class ViewVisibilityDebounceHandler(
  private val view: View,
  private val delayTimeInMili: Long = DEFAULT_DEBOUNCE_TIME_MILI
) {

  companion object {
    const val DEFAULT_DEBOUNCE_TIME_MILI = 500L
  }

  private var lastVisibilityValue = view.visibility

  private var job: Job? = null

  fun setVisible(isVisible: Boolean) {
    job?.cancel()
    job = view.findViewTreeLifecycleOwner()?.lifecycleScope?.launch {
      delay(delayTimeInMili)
      view.isVisible = isVisible
    }

  }
}

fun View.setVisibleWithDelay(
  newIsVisible: Boolean,
  delayTimeInMili: Long = ViewVisibilityDebounceHandler.DEFAULT_DEBOUNCE_TIME_MILI
) {
  val handler = ViewVisibilityDebounceHandler(this, delayTimeInMili)
  handler.setVisible(newIsVisible)
}