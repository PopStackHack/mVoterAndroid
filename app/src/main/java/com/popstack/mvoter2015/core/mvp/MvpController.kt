package com.popstack.mvoter2015.core.mvp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStore
import androidx.viewbinding.ViewBinding
import com.bluelinelabs.conductor.archlifecycle.ControllerLifecycleOwner
import com.popstack.mvoter2015.core.BaseController
import com.popstack.mvoter2015.di.Injectable
import com.popstack.mvoter2015.di.conductor.ConductorInjection
import timber.log.Timber
import javax.inject.Inject
import kotlin.reflect.KClass

abstract class MvpController<VB : ViewBinding, V : Viewable, VM : BaseViewModel<V>>(
  bundle: Bundle? = null
) :
  BaseController<VB>(bundle), Injectable, LifecycleOwner {

  @Inject
  lateinit var viewModelFactory: ViewModelProvider.Factory

  private val viewModelStore: ViewModelStore = ViewModelStore()

  protected val lifecycleOwner by lazy {
    ControllerLifecycleOwner(this)
  }

  protected abstract val viewModel: VM

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup,
    savedViewState: Bundle?
  ): View {
    ConductorInjection.inject(this)
    try {
      viewModel.attachView(this as V)
    } catch (exception: ClassCastException) {
      Timber.e(exception)
      throw InvalidMvpImplementationException()
    }
    return super.onCreateView(inflater, container, savedViewState)
  }

  override fun onDestroyView(view: View) {
    viewModel.detachView()
    super.onDestroyView(view)
  }

  override fun onDestroy() {
    super.onDestroy()
    viewModelStore.clear()
  }

  override fun getLifecycle(): Lifecycle {
    return lifecycleOwner.lifecycle
  }

  /**
   * Helper function for easily init of viewModel
   */
  protected inline fun <reified VM : BaseViewModel<V>> contractedViewModels(): Lazy<VM> =
    ViewModelLazy(VM::class)

  inner class ViewModelLazy<VM : ViewModel>(
    private val viewModelClass: KClass<VM>
  ) : Lazy<VM> {
    private var cached: VM? = null

    override val value: VM
      get() {
        var viewModel = cached
        if (viewModel == null) {
          viewModel =
            ViewModelProvider(
              viewModelStore,
              viewModelFactory
            ).get(viewModelClass.java)
          cached = viewModel
        }
        return viewModel
      }

    override fun isInitialized() = cached != null
  }

}