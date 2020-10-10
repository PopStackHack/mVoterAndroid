package com.popstack.mvoter2015.feature.voterlist

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.popstack.mvoter2015.databinding.ItemVoterListBinding
import com.popstack.mvoter2015.helper.extensions.inflater
import com.popstack.mvoter2015.helper.extensions.withSafeAdapterPosition

class VoterListLinkRecyclerViewAdapter(
  private val itemClick: (Pair<String, String>) -> Unit
) : RecyclerView.Adapter<VoterListLinkRecyclerViewAdapter.VoterListLinkViewHolder>() {

  private val linkList = VoterListLinks.LINKS

  class VoterListLinkViewHolder(val binding: ItemVoterListBinding) :
    RecyclerView.ViewHolder(binding.root)

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VoterListLinkViewHolder {
    val binding = ItemVoterListBinding.inflate(parent.inflater(), parent, false)
    val viewHolder = VoterListLinkViewHolder(binding)
    viewHolder.apply {
      itemView.setOnClickListener {
        withSafeAdapterPosition { position ->
          itemClick.invoke(linkList[position])
        }
      }
    }
    return viewHolder
  }

  override fun onBindViewHolder(holder: VoterListLinkViewHolder, position: Int) {
    val itemAtIndex = linkList[position]
    holder.binding.apply {
      tvStateRegion.text = itemAtIndex.first
    }
  }

  override fun getItemCount(): Int {
    return linkList.size
  }
}