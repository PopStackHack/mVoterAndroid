package com.popstack.mvoter2015.feature.party.listing

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.popstack.mvoter2015.databinding.ItemPartyPlaceholderBinding
import com.popstack.mvoter2015.helper.extensions.inflater

class PartyPlaceHolderRecyclerViewAdapter constructor(
  private val placeholderCount: Int = DEFAULT_ITEM_COUNT
) : RecyclerView.Adapter<PartyPlaceHolderRecyclerViewAdapter.PartyPlaceholderViewHolder>() {

  companion object {
    private const val DEFAULT_ITEM_COUNT = 10
  }

  class PartyPlaceholderViewHolder(binding: ItemPartyPlaceholderBinding) : RecyclerView.ViewHolder(binding.root)

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PartyPlaceholderViewHolder =
    PartyPlaceholderViewHolder(ItemPartyPlaceholderBinding.inflate(parent.inflater(), parent, false))

  override fun getItemCount(): Int = placeholderCount

  override fun onBindViewHolder(holder: PartyPlaceholderViewHolder, position: Int) = Unit //DO NOTHING
}