package com.popstack.mvoter2015.feature.candidate.listing

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import coil.api.load
import coil.size.Precision
import coil.size.Scale
import coil.transform.CircleCropTransformation
import com.popstack.mvoter2015.R
import com.popstack.mvoter2015.databinding.ItemCandidateBinding
import com.popstack.mvoter2015.databinding.ItemCandidateSectionBinding
import com.popstack.mvoter2015.domain.candidate.model.CandidateId
import com.popstack.mvoter2015.feature.candidate.listing.viewholders.CandidateItemViewHolder
import com.popstack.mvoter2015.helper.diff.diffCallBackWith
import com.popstack.mvoter2015.helper.extensions.inflater
import com.popstack.mvoter2015.helper.extensions.toPx
import com.popstack.mvoter2015.helper.extensions.withSafeAdapterPosition
import com.popstack.mvoter2015.helper.recyclerview.StickyHeaderDecoration

class CandidateListRecyclerViewAdapter constructor(
  private val onCandidateClicked: (CandidateId) -> Unit
) : ListAdapter<CandidateViewItem, CandidateItemViewHolder>(
  diffCallBackWith(
    areItemTheSame = { item1, item2 -> item1.id == item2.id },
    areContentsTheSame = { item1, item2 -> item1 == item2 }
  )
),
  StickyHeaderDecoration.StickyHeaderInterface {

  companion object {
    const val VIEW_TYPE_CANDIDATE = 1
    const val VIEW_TYPE_CANDIDATE_SECTION_TITLE = 2
  }

  override fun onCreateViewHolder(
    parent: ViewGroup,
    viewType: Int
  ) = when (viewType) {
    VIEW_TYPE_CANDIDATE ->
      CandidateListItemViewHolder(
        ItemCandidateBinding.inflate(parent.inflater(), parent, false)
      ).apply {
        itemView.setOnClickListener {
          withSafeAdapterPosition { position ->
            getItem(position)?.let { itemAtIndex ->
              onCandidateClicked(CandidateId(itemAtIndex.id))
            }
          }
        }
      }
    else -> EthnicContituencyTitleViewHolder(
      ItemCandidateSectionBinding.inflate(parent.inflater(), parent, false)
    )
  }

  override fun getItemViewType(position: Int): Int = when (getItem(position)) {
    is SmallCandidateViewItem -> VIEW_TYPE_CANDIDATE
    is CandidateSectionTitleViewItem -> VIEW_TYPE_CANDIDATE_SECTION_TITLE
  }

  override fun onBindViewHolder(
    holder: CandidateItemViewHolder,
    position: Int
  ) {
    holder.bind(getItem(position))
  }

  override fun getHeaderPositionForItem(itemPosition: Int): Int {
    if (getItemViewType(itemPosition) == VIEW_TYPE_CANDIDATE_SECTION_TITLE) return itemPosition
    for (i in itemPosition downTo 0) {
      if (getItemViewType(i) == VIEW_TYPE_CANDIDATE_SECTION_TITLE) return i
    }
    return -1
  }

  override fun getHeaderLayout(headerPosition: Int): Int {
    return R.layout.item_candidate_section
  }

  override fun bindHeaderData(header: View?, headerPosition: Int) {
    if (headerPosition >= 0 && headerPosition < currentList.size)
      header?.let {
        ItemCandidateSectionBinding.bind(header).tvTitle.text =
          (getItem(headerPosition) as? CandidateSectionTitleViewItem)?.value.orEmpty()
      }
  }

  override fun isHeader(itemPosition: Int): Boolean {
    return getItemViewType(itemPosition) == VIEW_TYPE_CANDIDATE_SECTION_TITLE
  }
}

class CandidateListItemViewHolder(val binding: ItemCandidateBinding) : CandidateItemViewHolder(
  binding.root
) {
  override fun bind(viewItem: CandidateViewItem) {
    val smallCandidateViewItem = viewItem as? SmallCandidateViewItem ?: return
    with(binding) {
      tvCandidateName.text = smallCandidateViewItem.name
      tvCandidatePartyName.text = smallCandidateViewItem.partyName
      ivCandidate.load(smallCandidateViewItem.photoUrl) {
        placeholder(R.drawable.placeholder_oval)
        error(R.drawable.placeholder_oval)
        transformations(CircleCropTransformation())
        scale(Scale.FILL)
      }
      ivCandidatePartySeal.load(smallCandidateViewItem.partySealImageUrl) {
        placeholder(R.drawable.party_seal_placeholder_rect)
        error(R.drawable.party_seal_placeholder_rect)
        scale(Scale.FIT)
      }
    }
  }
}

class EthnicContituencyTitleViewHolder(val binding: ItemCandidateSectionBinding) :
  CandidateItemViewHolder(binding.root) {
  override fun bind(viewItem: CandidateViewItem) {
    val title = viewItem as? CandidateSectionTitleViewItem ?: return
    binding.tvTitle.text = title.value
  }
}