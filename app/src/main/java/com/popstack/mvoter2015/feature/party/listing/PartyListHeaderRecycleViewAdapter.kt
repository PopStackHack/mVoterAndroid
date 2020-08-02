package com.popstack.mvoter2015.feature.party.listing

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.popstack.mvoter2015.databinding.ItemPartyHeaderBinding
import com.popstack.mvoter2015.helper.extensions.inflater

class PartyListHeaderRecycleViewAdapter :
  RecyclerView.Adapter<PartyListHeaderRecycleViewAdapter.PartyListHeaderViewHolder>() {

  class PartyListHeaderViewHolder(binding: ItemPartyHeaderBinding) :
    RecyclerView.ViewHolder(binding.root)

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PartyListHeaderViewHolder {
    return PartyListHeaderViewHolder(
      ItemPartyHeaderBinding.inflate(
        parent.inflater(),
        parent,
        false
      )
    )
  }

  override fun getItemCount(): Int {
    return 1
  }

  override fun onBindViewHolder(holder: PartyListHeaderViewHolder, position: Int) {
    //DO NOTHING
  }
}