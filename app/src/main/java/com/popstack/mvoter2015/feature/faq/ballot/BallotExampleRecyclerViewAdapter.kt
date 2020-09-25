package com.popstack.mvoter2015.feature.faq.ballot

import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.size.Scale
import com.popstack.mvoter2015.R
import com.popstack.mvoter2015.databinding.ItemBallotBinding
import com.popstack.mvoter2015.helper.diff.diffCallBackWith
import com.popstack.mvoter2015.helper.extensions.inflater
import com.popstack.mvoter2015.helper.extensions.setCompoundDrawableWithIntrinsicBoundsKt
import com.popstack.mvoter2015.helper.extensions.withSafeAdapterPosition

class BallotExampleRecyclerViewAdapter(
  val onImageClick: ((position: Int, imageUrl: String) -> Unit)
) :
  ListAdapter<BallotExampleViewItem, BallotExampleRecyclerViewAdapter.BallotExampleViewHolder>(
    diffCallBackWith(
      areItemTheSame = { item1, item2 -> item1.id == item2.id },
      areContentsTheSame = { item1, item2 -> item1 == item2 }
    )
  ) {

  class BallotExampleViewHolder(val binding: ItemBallotBinding) :
    RecyclerView.ViewHolder(binding.root) {
    val validColor = ContextCompat.getColor(itemView.context, R.color.green)
    val inValidColor = ContextCompat.getColor(itemView.context, R.color.text_error)
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BallotExampleViewHolder {
    val binding = ItemBallotBinding.inflate(parent.inflater(), parent, false)
    return BallotExampleViewHolder(binding).also { holder ->
      holder.binding.ivBallot.setOnClickListener { _ ->
        holder.withSafeAdapterPosition { position ->
          onImageClick(position, getItem(position).image)
        }
      }
    }
  }

  override fun onBindViewHolder(holder: BallotExampleViewHolder, position: Int) {
    val itemAtIndex = getItem(position)
    holder.binding.apply {
      if (itemAtIndex.isValid) {
        tvBallotValid.setText(R.string.valid_ballot)
        tvBallotValid.setCompoundDrawableWithIntrinsicBoundsKt(
          start = ContextCompat.getDrawable(holder.itemView.context, R.drawable.ic_check_circle_24)?.also {
            it.setTint(holder.validColor)
          }
        )
      } else {
        tvBallotValid.setText(R.string.invalid_ballot)
        tvBallotValid.setCompoundDrawableWithIntrinsicBoundsKt(
          start = ContextCompat.getDrawable(holder.itemView.context, R.drawable.ic_close_circle_24)?.also {
            it.setTint(holder.inValidColor)
          }
        )
      }
      ivBallot.load(itemAtIndex.image) {
        placeholder(R.drawable.placeholder_rect)
        scale(Scale.FIT)
      }
    }
  }
}