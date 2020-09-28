package com.popstack.mvoter2015.helper.search

import androidx.appcompat.widget.SearchView
import androidx.lifecycle.LifecycleCoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class DebounceSearchQueryListener(
  val onQuery: ((query: String?) -> Unit),
  val scope: LifecycleCoroutineScope,
  val debounceTimeInMili: Long = DEFAULT_DEBOUNCE_TIME_IN_MILI
) : SearchView.OnQueryTextListener {

  companion object {
    private const val DEFAULT_DEBOUNCE_TIME_IN_MILI = 500L
  }

  override fun onQueryTextSubmit(query: String?): Boolean {
    handleQuery(query)
    return true
  }

  override fun onQueryTextChange(query: String?): Boolean {
    handleQuery(query)
    return true
  }

  private var oldQuery: String? = null
  private var queryJob: Job? = null

  private fun handleQuery(query: String?) {
    if (query == oldQuery) {
      return
    }
    queryJob?.cancel()
    queryJob = scope.launch(Dispatchers.Main) {
      delay(debounceTimeInMili)
      onQuery.invoke(query)
    }
  }

}