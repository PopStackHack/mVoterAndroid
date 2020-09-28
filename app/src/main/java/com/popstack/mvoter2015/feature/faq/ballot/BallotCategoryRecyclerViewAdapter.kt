package com.popstack.mvoter2015.feature.faq.ballot

import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.popstack.mvoter2015.R
import com.popstack.mvoter2015.databinding.ItemBallotCategoryBinding
import com.popstack.mvoter2015.domain.faq.model.BallotExampleCategory
import com.popstack.mvoter2015.feature.faq.displayString
import com.popstack.mvoter2015.helper.extensions.inflater
import com.popstack.mvoter2015.helper.extensions.withSafeAdapterPosition

class BallotCategoryRecyclerViewAdapter(
  val onClickCategory: (BallotExampleCategory) -> Unit
) : RecyclerView.Adapter<BallotCategoryRecyclerViewAdapter.BallotCategoryViewHolder>() {

  private var selectedCategory: BallotExampleCategory? = null

  private val categoryList = listOf(
    BallotExampleCategory.NORMAL,
    BallotExampleCategory.ADVANCED
  )

  class BallotCategoryViewHolder(val binding: ItemBallotCategoryBinding) :
    RecyclerView.ViewHolder(binding.root)

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BallotCategoryViewHolder {

    return BallotCategoryViewHolder(
      ItemBallotCategoryBinding.inflate(
        parent.inflater(),
        parent,
        false
      )
    ).also { holder ->
      holder.itemView.setOnClickListener {
        holder.withSafeAdapterPosition { position ->
          onClickCategory.invoke(categoryList[position])
        }
      }
    }
  }

  fun setSelectedCategory(ballotExampleCategory: BallotExampleCategory) {
    selectedCategory = ballotExampleCategory
    notifyDataSetChanged()
  }

  override fun getItemCount(): Int {
    return categoryList.size
  }

  override fun onBindViewHolder(holder: BallotCategoryViewHolder, position: Int) {
    val category = categoryList[position]
    holder.binding.apply {

      tvCategory.setTextColor(
        if (category == selectedCategory) {
          viewSelectedIndicator.visibility = View.VISIBLE
          ContextCompat.getColor(holder.itemView.context, R.color.accent)
        } else {
          viewSelectedIndicator.visibility = View.INVISIBLE
          ContextCompat.getColor(holder.itemView.context, R.color.text_primary)
        }
      )

      tvCategory.text = category.displayString(holder.itemView.context)
    }
  }
}