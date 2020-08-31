package com.popstack.mvoter2015.feature.location

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.popstack.mvoter2015.core.recyclerview.ViewBindingViewHolder
import com.popstack.mvoter2015.databinding.ItemTownshipBinding
import com.popstack.mvoter2015.helper.diff.diffCallBackWith
import com.popstack.mvoter2015.helper.extensions.inflater

internal class TownshipRecyclerViewAdapter constructor(
  private val onTownshipClick: (String) -> Unit,
) : ListAdapter<TownshipViewItem, TownshipRecyclerViewAdapter.TownshipViewItemViewHolder>(
  diffCallBackWith(
    areItemTheSame = { item1, item2 ->
      item1.name == item2.name
    },
    areContentsTheSame = { item1, item2 ->
      item1 == item2
    }
  )
) {

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TownshipViewItemViewHolder {
    return TownshipViewItemViewHolder(
      ItemTownshipBinding.inflate(parent.inflater(), parent, false),
      onTownshipClick)
  }

  class TownshipViewItemViewHolder(
    binding: ItemTownshipBinding,
    onTownshipClick: (String) -> Unit
  ) : ViewBindingViewHolder<ItemTownshipBinding>(binding) {
    lateinit var townshipViewItem: TownshipViewItem

    init {
      binding.tvTownship.setOnClickListener {
        onTownshipClick(townshipViewItem.name)
      }
    }

    fun bind(townshipViewItem: TownshipViewItem) {
      this.townshipViewItem = townshipViewItem
      binding.tvTownship.text = townshipViewItem.name
    }
  }

  override fun onBindViewHolder(holder: TownshipViewItemViewHolder, position: Int) {
    holder.bind(getItem(position))
  }
}