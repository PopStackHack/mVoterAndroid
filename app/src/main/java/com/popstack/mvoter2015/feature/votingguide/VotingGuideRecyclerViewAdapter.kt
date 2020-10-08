package com.popstack.mvoter2015.feature.votingguide

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.popstack.mvoter2015.databinding.ItemHowToVoteCheckVoterListBinding
import com.popstack.mvoter2015.databinding.ItemHowToVoteHeaderBinding
import com.popstack.mvoter2015.databinding.ItemHowToVoteSectionTitleBinding
import com.popstack.mvoter2015.databinding.ItemHowToVoteStepBinding
import com.popstack.mvoter2015.feature.votingguide.viewholders.CheckVoterListViewHolder
import com.popstack.mvoter2015.feature.votingguide.viewholders.HeaderViewHolder
import com.popstack.mvoter2015.feature.votingguide.viewholders.SectionTitleViewHolder
import com.popstack.mvoter2015.feature.votingguide.viewholders.StepViewHolder
import com.popstack.mvoter2015.feature.votingguide.viewholders.VotingGuideViewHolder
import com.popstack.mvoter2015.helper.extensions.inflater

class VotingGuideRecyclerViewAdapter(
  private val viewItems: List<VotingGuideViewItem>,
  private val onCheckVoterListClick: () -> Unit
) :
  RecyclerView.Adapter<VotingGuideViewHolder>() {

  companion object {
    const val VIEW_TYPE_HEADER = 1
    const val VIEW_TYPE_VOTER_LIST = 2
    const val VIEW_TYPE_SECTION_TITLE = 3
    const val VIEW_TYPE_STEP = 4
  }

  override fun getItemViewType(position: Int): Int {
    return when (viewItems[position]) {
      is Header -> VIEW_TYPE_HEADER
      is CheckVoterList -> VIEW_TYPE_VOTER_LIST
      is SectionTitle -> VIEW_TYPE_SECTION_TITLE
      is Step -> VIEW_TYPE_STEP
    }
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VotingGuideViewHolder {
    return when (viewType) {
      VIEW_TYPE_HEADER -> HeaderViewHolder(
        ItemHowToVoteHeaderBinding.inflate(
          parent.inflater(),
          parent,
          false
        )
      )
      VIEW_TYPE_VOTER_LIST ->
        CheckVoterListViewHolder(
          ItemHowToVoteCheckVoterListBinding.inflate(
            parent.inflater(),
            parent,
            false
          )
        ).also {
          it.itemView.setOnClickListener {
            onCheckVoterListClick.invoke()
          }
        }
      VIEW_TYPE_SECTION_TITLE -> SectionTitleViewHolder(
        ItemHowToVoteSectionTitleBinding.inflate(
          parent.inflater(),
          parent,
          false
        )
      )
      VIEW_TYPE_STEP -> StepViewHolder(
        ItemHowToVoteStepBinding.inflate(
          parent.inflater(),
          parent,
          false
        )
      )
      else -> StepViewHolder(
        ItemHowToVoteStepBinding.inflate(
          parent.inflater(),
          parent,
          false
        )
      )
    }
  }

  override fun onBindViewHolder(holder: VotingGuideViewHolder, position: Int) {
    holder.bind(viewItems[position])
  }

  override fun getItemCount(): Int = viewItems.size

}