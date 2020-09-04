package com.popstack.mvoter2015.feature.candidate.listing

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import coil.size.Scale
import coil.transform.CircleCropTransformation
import com.popstack.mvoter2015.R
import com.popstack.mvoter2015.databinding.ItemCandidateBinding
import com.popstack.mvoter2015.domain.candidate.model.CandidateId
import com.popstack.mvoter2015.helper.diff.diffCallBackWith
import com.popstack.mvoter2015.helper.extensions.inflater
import com.popstack.mvoter2015.helper.extensions.withSafeAdapterPosition

class CandidateListRecyclerViewAdapter constructor(
  private val onCandidateClicked: (CandidateId) -> Unit
) : ListAdapter<CandidateListViewItem.SmallCandidateViewItem, CandidateListItemViewHolder>(
  diffCallBackWith(
    areItemTheSame = { item1, item2 -> item1.id == item2.id },
    areContentsTheSame = { item1, item2 -> item1 == item2 }
  )
) {
  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = CandidateListItemViewHolder(
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

  override fun onBindViewHolder(holder: CandidateListItemViewHolder, position: Int) {
    holder.bind(getItem(position))
  }

}

class CandidateListItemViewHolder(val binding: ItemCandidateBinding) : RecyclerView.ViewHolder(binding.root) {
  fun bind(smallCandidateViewItem: CandidateListViewItem.SmallCandidateViewItem) {
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