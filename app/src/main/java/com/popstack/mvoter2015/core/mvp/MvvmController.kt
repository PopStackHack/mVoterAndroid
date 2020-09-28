package com.popstack.mvoter2015.core.mvp

import android.os.Bundle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStore
import androidx.viewbinding.ViewBinding
import com.popstack.mvoter2015.core.LifeCycleAwareController
import com.popstack.mvoter2015.di.Injectable
import javax.inject.Inject
import kotlin.reflect.KClass

abstract class MvvmController<VB : ViewBinding>(args: Bundle? = null) :
  LifeCycleAwareController<VB>(args), Injectable {

  @Inject
  lateinit var viewModelFactory: ViewModelProvider.Factory

  protected val viewModelStore: ViewModelStore = ViewModelStore()

  override fun onDestroy() {
    super.onDestroy()
    viewModelStore.clear()
  }

  protected inline fun <reified VM : ViewModel> viewModels(
    store: ViewModelStore = viewModelStore
  ): Lazy<VM> = ViewModelLazy(VM::class, store)

  inner class ViewModelLazy<VM : ViewModel>(
    private val viewModelClass: KClass<VM>,
    private val store: ViewModelStore = viewModelStore
  ) : Lazy<VM> {
    private var cached: VM? = null

    override val value: VM
      get() {
        var viewModel = cached
        if (viewModel == null) {
          viewModel =
            ViewModelProvider(
              store,
              viewModelFactory
            ).get(viewModelClass.java)
          cached = viewModel
        }
        return viewModel
      }

    override fun isInitialized() = cached != null
  }
}