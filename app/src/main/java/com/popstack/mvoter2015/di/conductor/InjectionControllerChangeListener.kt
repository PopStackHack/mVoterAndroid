package com.popstack.mvoter2015.di.conductor

import android.view.ViewGroup
import com.bluelinelabs.conductor.Controller
import com.bluelinelabs.conductor.ControllerChangeHandler
import com.popstack.mvoter2015.di.Injectable

/**
 * Change Listener that handle Dagger injection when moving between controllers
 */
class InjectionControllerChangeListener : ControllerChangeHandler.ControllerChangeListener {

  override fun onChangeStarted(
    to: Controller?,
    from: Controller?,
    isPush: Boolean,
    container: ViewGroup,
    handler: ControllerChangeHandler
  ) {
    super.onChangeStarted(to, from, isPush, container, handler)
    if (to != null && to is Injectable) {
      ConductorInjection.inject(to)
    }
  }

}