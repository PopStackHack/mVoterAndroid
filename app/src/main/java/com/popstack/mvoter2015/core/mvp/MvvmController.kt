package com.popstack.mvoter2015.core.mvp

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding
import com.bluelinelabs.conductor.archlifecycle.ControllerLifecycleOwner
import com.popstack.mvoter2015.core.BaseController
import com.popstack.mvoter2015.helper.conductor.requireActivityAsAppCompatActivity
import kotlin.reflect.KClass

abstract class MvvmController<VB : ViewBinding>(args: Bundle? = null) :
  BaseController<VB>(args), LifecycleOwner {

  protected val lifecycleOwner by lazy {
    ControllerLifecycleOwner(this)
  }

  override fun onDestroyView(view: View) {
    super.onDestroyView(view)
  }

  override fun onDestroy() {
    super.onDestroy()
  }

  override fun getLifecycle(): Lifecycle {
    return lifecycleOwner.lifecycle
  }

  protected inline fun <reified VM : ViewModel> viewModels(
    factory: ViewModelProvider.Factory? = null
  ): Lazy<VM> =
    if (factory == null) {
      ViewModelLazy(VM::class)
    } else {
      ViewModelLazy(VM::class, factory)
    }

  inner class ViewModelLazy<VM : ViewModel>(
    private val viewModelClass: KClass<VM>,
    private val factory: ViewModelProvider.Factory? = null
  ) : Lazy<VM> {
    private var cached: VM? = null

    override val value: VM
      get() {
        var viewModel = cached
        if (viewModel == null) {
          viewModel =
            ViewModelProvider(
              requireActivityAsAppCompatActivity().viewModelStore,
              factory ?: requireActivityAsAppCompatActivity().defaultViewModelProviderFactory
            ).get(viewModelClass.java)
          cached = viewModel
        }
        return viewModel
      }

    override fun isInitialized() = cached != null
  }
}