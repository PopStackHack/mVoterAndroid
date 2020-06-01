package com.popstack.mvoter2015.core

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import com.bluelinelabs.conductor.Controller

abstract class BaseController<VB : ViewBinding> constructor(
  bundle: Bundle? = null
) : Controller(bundle) {

  private var _binding: ViewBinding? = null

  @Suppress("UNCHECKED_CAST")
  protected val binding
    get() = _binding!! as VB

  protected abstract val bindingInflater: (LayoutInflater, ViewGroup) -> VB

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup,
    savedViewState: Bundle?
  ): View {
    _binding = bindingInflater(inflater, container)
    onBindView()
    return binding.root
  }

  //Function to safely call after on create and before onViewCreated
  protected open fun onBindView() {
    //Do Nothing
  }

  override fun onDestroyView(view: View) {
    _binding = null
    super.onDestroyView(view)
  }

}