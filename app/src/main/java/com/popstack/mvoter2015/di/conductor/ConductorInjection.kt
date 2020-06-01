package com.popstack.mvoter2015.di.conductor

import com.bluelinelabs.conductor.Controller
import dagger.android.HasAndroidInjector
import java.lang.UnsupportedOperationException

/**
 * Modified from https://github.com/Bodo1981/conductor-dagger/blob/master/library/src/main/java/com/christianbahl/conductor/ConductorInjection.java
 */
object ConductorInjection {

  fun inject(controller: Controller) {

    val activity = controller.activity!!

    if (activity is HasAndroidInjector) {
      activity.androidInjector().inject(controller)
    } else {
      val application = activity.application

      if (application is HasAndroidInjector) {
        application.androidInjector().inject(controller)
      } else {
        throw UnsupportedOperationException("Either activity or android need to implement AndroidInjector")
      }
    }
  }

}