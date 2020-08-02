package com.popstack.mvoter2015.feature.party.detail

import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.popstack.mvoter2015.databinding.ItemPartyTimelineBinding
import com.popstack.mvoter2015.helper.diff.diffCallBackWith
import com.popstack.mvoter2015.helper.extensions.inflater
import java.time.format.DateTimeFormatter
import java.util.Locale

class PartyTimelineRecyclerViewAdapter :
  ListAdapter<PartyTimelineViewItem, PartyTimelineRecyclerViewAdapter.PartyTimelineViewHolder>(
    diffCallBackWith(areItemTheSame = { item1, item2 -> item1.event == item2.event },
      areContentsTheSame = { item1, item2 ->
        item1 == item2
      })
  ) {

  class PartyTimelineViewHolder(val binding: ItemPartyTimelineBinding) :
    RecyclerView.ViewHolder(binding.root)

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PartyTimelineViewHolder {
    val holder =
      PartyTimelineViewHolder(ItemPartyTimelineBinding.inflate(parent.inflater(), parent, false))
    return holder
  }

  override fun onBindViewHolder(holder: PartyTimelineViewHolder, position: Int) {
    val itemAtIndex = getItem(position)
    holder.binding.apply {
      lineTop.isVisible = position != 0 //Don't show on first index
      lineBottom.isVisible = position != itemCount - 1 //Don't show on last index
      tvDate.text =
        itemAtIndex.date.format(DateTimeFormatter.ofPattern("dd MMM '\n'yyyy", Locale.ENGLISH))
      tvEvent.text = when (itemAtIndex.event) {
        TimelineEvent.ESTABLISHMENT_APPLICATION -> TODO()
        TimelineEvent.ESTABLISHMENT_APPROVAL -> TODO()
        TimelineEvent.REGISTRATION_APPLICATION -> TODO()
        TimelineEvent.REGISTRATION_APPROVAL -> TODO()
      }
    }
  }
}