package com.popstack.mvoter2015.feature.faq.search

import android.text.TextUtils
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import com.popstack.mvoter2015.databinding.ItemFaqBinding
import com.popstack.mvoter2015.domain.faq.model.FaqId
import com.popstack.mvoter2015.helper.diff.diffCallBackWith
import com.popstack.mvoter2015.helper.extensions.inflater
import com.popstack.mvoter2015.helper.extensions.withSafeAdapterPosition

class FaqSearchPagingAdapter(
  private val share: (FaqId, @ParameterName("position") Int) -> Unit
) :
  PagingDataAdapter<FaqSearchViewItem, FaqSearchPagingAdapter.FaqSearchViewHolder>(
    diffCallBackWith(
      areItemTheSame = { item1, item2 ->
        item1.faqId == item2.faqId
      },
      areContentsTheSame = { item1, item2 ->
        item1 == item2
      }
    )
  ) {

  /**
   * Set that remembers which faq has been expanded by user
   * This is used in [onBindViewHolder] to determine whether a view has been expanded or not
   */
  private val expandedFaqSet = mutableSetOf<FaqId>()

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FaqSearchViewHolder {
    val binding = ItemFaqBinding.inflate(parent.inflater(), parent, false)
    return FaqSearchViewHolder(binding).also { holder ->
      holder.itemView.setOnClickListener {
        holder.withSafeAdapterPosition { position ->
          getItem(position)?.faqId?.let { faqId ->
            if (expandedFaqSet.contains(faqId)) {
              expandedFaqSet.remove(faqId)
            } else {
              expandedFaqSet.add(faqId)
            }
            notifyItemChanged(position)
          }

        }
      }

      holder.binding.ivShare.setOnClickListener {
        holder.withSafeAdapterPosition { position ->
          getItem(position)?.let { itemAtIndex ->
            share(itemAtIndex.faqId, position)
          }
        }
      }
    }
  }

  override fun onBindViewHolder(holder: FaqSearchViewHolder, position: Int) {
    getItem(position)?.let { itemAtIndex ->
      holder.binding.apply {
        tvQuestion.text = itemAtIndex.question
        tvAnswer.text = itemAtIndex.answer

        if (expandedFaqSet.contains(itemAtIndex.faqId)) {
          tvAnswer.maxLines = Int.MAX_VALUE
          tvAnswer.ellipsize = null
          ivShare.isVisible = true
        } else {
          tvAnswer.maxLines = 2
          tvAnswer.ellipsize = TextUtils.TruncateAt.END
          tvAnswer.ellipsize
          ivShare.isVisible = false
        }
      }
    }
  }

  class FaqSearchViewHolder(val binding: ItemFaqBinding) : RecyclerView.ViewHolder(binding.root)
}