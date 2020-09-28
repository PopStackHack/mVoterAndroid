package com.popstack.mvoter2015.core

import android.os.Bundle
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.viewbinding.ViewBinding
import com.bluelinelabs.conductor.archlifecycle.ControllerLifecycleOwner

abstract class LifeCycleAwareController<VB : ViewBinding>(args: Bundle? = null) :
  BaseController<VB>(args), LifecycleOwner {

  protected val lifecycleOwner by lazy {
    ControllerLifecycleOwner(this)
  }

  override fun getLifecycle(): Lifecycle {
    return lifecycleOwner.lifecycle
  }

}