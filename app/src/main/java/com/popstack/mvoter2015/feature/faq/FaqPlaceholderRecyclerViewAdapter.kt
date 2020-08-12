package com.popstack.mvoter2015.feature.faq

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.popstack.mvoter2015.databinding.ItemFaqPlaceholderBinding
import com.popstack.mvoter2015.helper.extensions.inflater

class FaqPlaceholderRecyclerViewAdapter constructor(
  private val placeholderCount: Int = DEFAULT_ITEM_COUNT
) : RecyclerView.Adapter<FaqPlaceholderRecyclerViewAdapter.FaqPlaceholderViewHolder>() {

  companion object {
    private const val DEFAULT_ITEM_COUNT = 10
  }

  class FaqPlaceholderViewHolder(binding: ItemFaqPlaceholderBinding) : RecyclerView.ViewHolder(binding.root)

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FaqPlaceholderViewHolder =
    FaqPlaceholderViewHolder(ItemFaqPlaceholderBinding.inflate(parent.inflater(), parent, false))

  override fun getItemCount(): Int = placeholderCount

  override fun onBindViewHolder(holder: FaqPlaceholderViewHolder, position: Int) = Unit //DO NOTHING
}