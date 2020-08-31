package com.popstack.mvoter2015.feature.candidate.listing

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import coil.size.Scale
import com.popstack.mvoter2015.databinding.ItemCandidateBinding
import com.popstack.mvoter2015.helper.diff.diffCallBackWith
import com.popstack.mvoter2015.helper.extensions.inflater

class CandidateListRecyclerViewAdapter
  : ListAdapter<CandidateListViewItem.SmallCandidateViewItem, CandidateListItemViewHolder>(
  diffCallBackWith(areItemTheSame = { item1, item2 -> item1.id == item2.id },
    areContentsTheSame = { item1, item2 ->
      item1 == item2
    })
) {
  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = CandidateListItemViewHolder(
    ItemCandidateBinding.inflate(parent.inflater(), parent, false)
  )

  override fun onBindViewHolder(holder: CandidateListItemViewHolder, position: Int) {
    holder.bind(getItem(position))
  }

}

class CandidateListItemViewHolder(val binding: ItemCandidateBinding) : RecyclerView.ViewHolder(binding.root) {
  fun bind(smallCandidateViewItem: CandidateListViewItem.SmallCandidateViewItem) {
    with(binding) {
      tvCandidateName.text = smallCandidateViewItem.name
      tvCandidatePartyName.text = smallCandidateViewItem.partyName
//      ivCandidate.load(smallCandidateViewItem.photoUrl) {
//        scale(Scale.FIT)
//        crossfade(true)
//      }
      ivCandidatePartyFlag.load(smallCandidateViewItem.partyImageUrl) {
        scale(Scale.FILL)
        crossfade(true)
      }
    }
  }
}