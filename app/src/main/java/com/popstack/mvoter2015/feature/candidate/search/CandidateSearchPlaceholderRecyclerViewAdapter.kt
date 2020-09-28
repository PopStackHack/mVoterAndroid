package com.popstack.mvoter2015.feature.candidate.search

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.popstack.mvoter2015.databinding.ItemCandidatePlaceholderBinding
import com.popstack.mvoter2015.helper.extensions.inflater

class CandidateSearchPlaceholderRecyclerViewAdapter constructor(
  private val placeholderCount: Int = DEFAULT_ITEM_COUNT
) : RecyclerView.Adapter<CandidateSearchPlaceholderRecyclerViewAdapter.CandidatePlaceholderViewHolder>() {

  companion object {
    private const val DEFAULT_ITEM_COUNT = 10
  }

  class CandidatePlaceholderViewHolder(binding: ItemCandidatePlaceholderBinding) : RecyclerView.ViewHolder(binding.root)

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CandidatePlaceholderViewHolder =
    CandidatePlaceholderViewHolder(ItemCandidatePlaceholderBinding.inflate(parent.inflater(), parent, false))

  override fun getItemCount(): Int = placeholderCount

  override fun onBindViewHolder(holder: CandidatePlaceholderViewHolder, position: Int) = Unit //DO NOTHING
}