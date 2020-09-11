package com.popstack.mvoter2015.feature.candidate.listing

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import coil.api.load
import coil.size.Scale
import coil.transform.CircleCropTransformation
import com.popstack.mvoter2015.R
import com.popstack.mvoter2015.databinding.ItemCandidateBinding
import com.popstack.mvoter2015.databinding.ItemEthnicConstituencyBinding
import com.popstack.mvoter2015.domain.candidate.model.CandidateId
import com.popstack.mvoter2015.feature.candidate.listing.viewholders.CandidateItemViewHolder
import com.popstack.mvoter2015.helper.diff.diffCallBackWith
import com.popstack.mvoter2015.helper.extensions.inflater
import com.popstack.mvoter2015.helper.extensions.withSafeAdapterPosition

class CandidateListRecyclerViewAdapter constructor(
  private val onCandidateClicked: (CandidateId) -> Unit
) : ListAdapter<CandidateViewItem, CandidateItemViewHolder>(
  diffCallBackWith(
    areItemTheSame = { item1, item2 -> item1.id == item2.id },
    areContentsTheSame = { item1, item2 -> item1 == item2 }
  )
) {

  companion object {
    const val VIEW_TYPE_CANDIDATE = 1
    const val VIEW_TYPE_ETHNIC_CONSTITUENCY_TITLE = 2
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = when (viewType) {
    VIEW_TYPE_CANDIDATE -> CandidateListItemViewHolder(
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
      ItemEthnicConstituencyBinding.inflate(parent.inflater(), parent, false)
    )
  }

  override fun getItemViewType(position: Int): Int = when (getItem(position)) {
    is SmallCandidateViewItem -> VIEW_TYPE_CANDIDATE
    is EthnicConstituencyTitleViewItem -> VIEW_TYPE_ETHNIC_CONSTITUENCY_TITLE
  }


  override fun onBindViewHolder(holder: CandidateItemViewHolder, position: Int) {
    holder.bind(getItem(position))
  }
}

class CandidateListItemViewHolder(val binding: ItemCandidateBinding) : CandidateItemViewHolder(binding.root) {
  override fun bind(viewItem: CandidateViewItem) {
    val smallCandidateViewItem = viewItem as? SmallCandidateViewItem ?: return
    with(binding) {
      tvCandidateName.text = smallCandidateViewItem.name
      tvCandidatePartyName.text = smallCandidateViewItem.partyName
      ivCandidate.load(smallCandidateViewItem.photoUrl) {
        error(R.drawable.placeholder_oval)
        transformations(CircleCropTransformation())
        scale(Scale.FILL)
      }
      ivCandidatePartySeal.load(smallCandidateViewItem.partySealImageUrl) {
        placeholder(R.drawable.placeholder_rect)
        error(R.drawable.placeholder_rect)
        scale(Scale.FILL)
        crossfade(true)
      }
    }
  }
}

class EthnicContituencyTitleViewHolder(val binding: ItemEthnicConstituencyBinding) : CandidateItemViewHolder(binding.root) {
  override fun bind(viewItem: CandidateViewItem) {
    val title = viewItem as? EthnicConstituencyTitleViewItem ?: return
    binding.tvTitle.text = title.value
  }
}