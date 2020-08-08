package com.popstack.mvoter2015.feature.votingguide.viewholders

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.popstack.mvoter2015.feature.votingguide.VotingGuideViewItem

abstract class VotingGuideViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
  abstract fun bind(viewItem: VotingGuideViewItem)
}