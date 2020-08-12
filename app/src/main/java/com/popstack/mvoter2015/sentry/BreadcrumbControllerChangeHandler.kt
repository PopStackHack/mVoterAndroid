package com.popstack.mvoter2015.sentry

import android.view.ViewGroup
import com.bluelinelabs.conductor.Controller
import com.bluelinelabs.conductor.ControllerChangeHandler
import io.sentry.core.Breadcrumb
import io.sentry.core.Sentry

object BreadcrumbControllerChangeHandler : ControllerChangeHandler.ControllerChangeListener {

  private const val BREADCRUMB_CATEGORY_CONTROLLER = "controller_change"
  private const val DATA_FROM = "from"
  private const val DATA_TO = "to"
  private const val ARGUMENTS = "args"
  private const val IS_PUSH = "is_push"

  override fun onChangeCompleted(
    to: Controller?,
    from: Controller?,
    isPush: Boolean,
    container: ViewGroup,
    handler: ControllerChangeHandler
  ) {
    val breadcrumb = Breadcrumb()
    breadcrumb.type = BREADCRUMB_CATEGORY_CONTROLLER
    breadcrumb.setData(DATA_FROM, from?.let(::getControllerName) ?: "")
    breadcrumb.setData(DATA_TO, to?.let(::getControllerName) ?: "")
    breadcrumb.setData(ARGUMENTS, to?.args?.toString() ?: "")
    breadcrumb.setData(IS_PUSH, isPush)
    Sentry.addBreadcrumb(breadcrumb)
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