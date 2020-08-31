package com.popstack.mvoter2015.di.conductor

import android.view.ViewGroup
import com.bluelinelabs.conductor.Controller
import com.bluelinelabs.conductor.ControllerChangeHandler
import com.popstack.mvoter2015.di.Injectable

object ControllerInjectorChangeHandler : ControllerChangeHandler.ControllerChangeListener {

  override fun onChangeStarted(to: Controller?, from: Controller?, isPush: Boolean, container: ViewGroup, handler: ControllerChangeHandler) {
    if (to != null && to is Injectable) {
      ConductorInjection.inject(to)
    }
    super.onChangeStarted(to, from, isPush, container, handler)
  }

}