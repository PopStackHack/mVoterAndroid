package com.popstack.mvoter2015.feature.candidate.listing.viewholders

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.popstack.mvoter2015.feature.candidate.listing.CandidateViewItem

abstract class CandidateItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
  abstract fun bind(viewItem: CandidateViewItem)
}