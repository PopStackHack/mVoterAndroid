package com.popstack.mvoter2015.core

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.annotation.MenuRes
import androidx.viewbinding.ViewBinding
import com.bluelinelabs.conductor.Controller
import com.popstack.mvoter2015.di.Injectable
import com.popstack.mvoter2015.di.conductor.ConductorInjection
import com.popstack.mvoter2015.feature.analytics.screen.CanTrackScreen
import com.popstack.mvoter2015.feature.analytics.screen.ScreenTrackAnalyticsProvider
import com.popstack.mvoter2015.helper.conductor.requireContext

abstract class BaseController<VB : ViewBinding> constructor(
  bundle: Bundle? = null
) : Controller(bundle) {

  private var _binding: ViewBinding? = null

  @Suppress("UNCHECKED_CAST")
  protected val binding
    get() = _binding!! as VB

  protected abstract val bindingInflater: (LayoutInflater) -> VB

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup,
    savedViewState: Bundle?
  ): View {
    if (this is Injectable) {
      ConductorInjection.inject(this)
    }
    _binding = bindingInflater(inflater)
    onBindView(savedViewState)
    return binding.root
  }

  //Function to safely call after on create and before onViewCreated
  protected open fun onBindView(savedViewState: Bundle?) {
    //Do Nothing
  }

  override fun onAttach(view: View) {
    super.onAttach(view)
    if (this is CanTrackScreen) {
      ScreenTrackAnalyticsProvider.screenTackAnalytics(requireContext())
        .trackScreen(this)
    }
  }

  override fun onDestroyView(view: View) {
    _binding = null
    super.onDestroyView(view)
  }

  /**
   * A easy way to handle menu without having to write code in three places
   */
  @MenuRes
  private var menuResId: Int? = null
  private var menuItemClick: (MenuItem) -> Boolean = { false }

  protected fun setHasOptionsMenu(@MenuRes menuResId: Int, menuItemClick: (MenuItem) -> Boolean) {
    setHasOptionsMenu(true)
    this.menuResId = menuResId
    this.menuItemClick = menuItemClick
  }

  override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
    if (menuResId != null) {
      inflater.inflate(menuResId!!, menu)
    }
    super.onCreateOptionsMenu(menu, inflater)
  }

  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    return menuItemClick.invoke(item)
  }

}