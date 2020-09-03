package com.popstack.mvoter2015.feature.appupdate

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.popstack.mvoter2015.databinding.BottomSheetRelaxedAppUpdateBinding

class RelaxedAppUpdateBottomSheet : BottomSheetDialogFragment() {

  private var _binding: BottomSheetRelaxedAppUpdateBinding? = null
  private val binding get() = _binding!!

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    _binding = BottomSheetRelaxedAppUpdateBinding.inflate(inflater, container, false)
    val view = binding.root
    return view
  }

  var onOkayClick: (() -> Unit)? = null
  var onCancelClick: (() -> Unit)? = null

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    binding.buttonOkay.setOnClickListener {
      onOkayClick?.invoke()
    }

    binding.buttonCancel.setOnClickListener {
      onCancelClick?.invoke()
    }

  }

  override fun onDismiss(dialog: DialogInterface) {
    super.onDismiss(dialog)
  }

  override fun onDestroyView() {
    super.onDestroyView()
    _binding = null
    onOkayClick = null
    onCancelClick = null
  }
}