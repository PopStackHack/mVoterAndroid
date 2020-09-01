package com.popstack.mvoter2015.feature.candidate.search

import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import coil.api.load
import com.popstack.mvoter2015.R
import com.popstack.mvoter2015.core.recyclerview.ViewBindingViewHolder
import com.popstack.mvoter2015.databinding.ItemCandidateBinding
import com.popstack.mvoter2015.domain.candidate.model.CandidateId
import com.popstack.mvoter2015.feature.candidate.listing.CandidateListViewItem
import com.popstack.mvoter2015.helper.diff.diffCallBackWith
import com.popstack.mvoter2015.helper.extensions.inflater
import com.popstack.mvoter2015.helper.extensions.withSafeAdapterPosition

internal class CandidateSearchPagingAdapter constructor(
  private val itemClick: (CandidateId) -> Unit
) :
  PagingDataAdapter<CandidateListViewItem.SmallCandidateViewItem, CandidateSearchPagingAdapter.CandidateSearchResultViewItemViewHolder>(
    diffCallBackWith(
      areItemTheSame = { item1, item2 ->
        item1.id == item2.id
      },
      areContentsTheSame = { item1, item2 ->
        item1 == item2
      }
    )
  ) {

  override fun onCreateViewHolder(
    parent: ViewGroup,
    viewType: Int
  ): CandidateSearchResultViewItemViewHolder {
    val binding = ItemCandidateBinding.inflate(parent.inflater(), parent, false)
    val viewHolder = CandidateSearchResultViewItemViewHolder(binding)
    viewHolder.apply {
      itemView.setOnClickListener {
        withSafeAdapterPosition { position ->
          getItem(position)?.let { itemAtIndex ->
            itemClick.invoke(CandidateId(itemAtIndex.id))
          }
        }
      }
    }
    return viewHolder
  }

  override fun onBindViewHolder(
    holder: CandidateSearchResultViewItemViewHolder,
    position: Int
  ) {
    getItem(position)?.let { itemAtIndex ->
      holder.binding.apply {
        ivCandidate.load(itemAtIndex.photoUrl) {
          placeholder(R.drawable.placeholder_oval)
          error(R.drawable.placeholder_oval)
          crossfade(true)
        }

        tvCandidateName.text = itemAtIndex.name
        tvCandidatePartyName.text = itemAtIndex.partyName

        ivCandidatePartyFlag.load(itemAtIndex.partyImageUrl) {
          placeholder(R.drawable.placeholder_rect)
          error(R.drawable.placeholder_rect)
          crossfade(true)
        }
      }
    }
  }

  class CandidateSearchResultViewItemViewHolder(
    binding: ItemCandidateBinding
  ) : ViewBindingViewHolder<ItemCandidateBinding>(binding)
}