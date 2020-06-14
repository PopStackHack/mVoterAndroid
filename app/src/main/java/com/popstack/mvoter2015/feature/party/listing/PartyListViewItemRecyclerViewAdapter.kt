package com.popstack.mvoter2015.feature.party.listing

import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import coil.api.load
import com.popstack.mvoter2015.R
import com.popstack.mvoter2015.core.recyclerview.ViewBindingViewHolder
import com.popstack.mvoter2015.databinding.ItemPartyBinding
import com.popstack.mvoter2015.domain.party.model.PartyId
import com.popstack.mvoter2015.feature.party.listing.PartyListViewItemRecyclerViewAdapter.PartyListViewItemViewHolder
import com.popstack.mvoter2015.helper.diff.diffCallBackWith
import com.popstack.mvoter2015.helper.extensions.inflater
import com.popstack.mvoter2015.helper.extensions.withSafeAdapterPosition

internal class PartyListViewItemRecyclerViewAdapter constructor(
  private val itemClickListener: PartyListItemClickListener
) : PagingDataAdapter<PartyListViewItem, PartyListViewItemViewHolder>(
  diffCallBackWith(
    areItemTheSame = { item1, item2 ->
      item1.partyId == item2.partyId
    },
    areContentsTheSame = { item1, item2 ->
      item1 == item2
    }
  )
) {

  internal interface PartyListItemClickListener {

    fun onItemClick(partyId: PartyId)

  }

  override fun onCreateViewHolder(
    parent: ViewGroup,
    viewType: Int
  ): PartyListViewItemViewHolder {
    val binding = ItemPartyBinding.inflate(parent.inflater(), parent, false)
    val viewHolder = PartyListViewItemViewHolder(binding)
    viewHolder.apply {
      itemView.setOnClickListener {
        withSafeAdapterPosition { position ->
          getItem(position)?.let { itemAtIndex ->
            itemClickListener.onItemClick(itemAtIndex?.partyId)
          }
        }
      }
    }
    return viewHolder
  }

  override fun onBindViewHolder(
    holder: PartyListViewItemViewHolder,
    position: Int
  ) {
    getItem(position)?.let { itemAtIndex ->
      holder.binding.apply {
        ivPartyFlag.load(itemAtIndex.flagImageUrl) {
          placeholder(R.drawable.placeholder_rect)
          crossfade(true)
        }

        tvPartyName.text = itemAtIndex.name
        tvPartyRegion.text = itemAtIndex.region
      }
    }
  }

  class PartyListViewItemViewHolder(
    binding: ItemPartyBinding
  ) : ViewBindingViewHolder<ItemPartyBinding>(binding)
}