package com.popstack.mvoter2015.logging

import android.view.ViewGroup
import com.bluelinelabs.conductor.Controller
import com.bluelinelabs.conductor.ControllerChangeHandler
import com.google.firebase.crashlytics.FirebaseCrashlytics

object BreadcrumbControllerChangeHandler : ControllerChangeHandler.ControllerChangeListener {

  override fun onChangeCompleted(
    to: Controller?,
    from: Controller?,
    isPush: Boolean,
    container: ViewGroup,
    handler: ControllerChangeHandler
  ) {
    val fromControllerName = from?.let(::getControllerName)
    val toControllerName = to?.let(::getControllerName)
    val arguments = to?.let { to.args }
    FirebaseCrashlytics.getInstance().log(
      "Controller Navigation : From $fromControllerName " +
        "To $toControllerName " +
        "With arguments: $arguments " +
        "And push set to $isPush"
    )
    super.onChangeCompleted(to, from, isPush, container, handler)
  }

  private fun getControllerName(controller: Controller): String {
    if (controller is HasTag) {
      return controller.tag
    } else {
      return controller.javaClass.simpleName
    }
  }
}