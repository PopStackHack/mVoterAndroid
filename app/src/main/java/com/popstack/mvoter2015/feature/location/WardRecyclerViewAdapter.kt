package com.popstack.mvoter2015.feature.location

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.popstack.mvoter2015.core.recyclerview.ViewBindingViewHolder
import com.popstack.mvoter2015.databinding.ItemWardBinding
import com.popstack.mvoter2015.helper.diff.diffCallBackWith
import com.popstack.mvoter2015.helper.extensions.inflater

internal class WardRecyclerViewAdapter constructor(
  private val onWardClick: (String) -> Unit
) : ListAdapter<String, WardRecyclerViewAdapter.WardViewItemViewHolder>(
  diffCallBackWith(
    areItemTheSame = { item1, item2 ->
      item1 == item2
    },
    areContentsTheSame = { item1, item2 ->
      item1 == item2
    }
  )
) {

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WardViewItemViewHolder {
    return WardViewItemViewHolder(
      ItemWardBinding.inflate(parent.inflater(), parent, false),
      onWardClick)
  }

  class WardViewItemViewHolder(
    binding: ItemWardBinding,
    onWardClick: (String) -> Unit
  ) : ViewBindingViewHolder<ItemWardBinding>(binding) {
    lateinit var ward: String

    init {
      binding.tvWard.setOnClickListener {
        onWardClick(ward)
      }
    }

    fun bind(ward: String) {
      this.ward = ward
      binding.tvWard.text = ward
    }
  }

  override fun onBindViewHolder(holder: WardViewItemViewHolder, position: Int) {
    holder.bind(getItem(position))
  }
}