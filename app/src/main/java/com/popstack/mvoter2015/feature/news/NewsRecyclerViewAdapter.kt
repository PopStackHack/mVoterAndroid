package com.popstack.mvoter2015.feature.news

import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import com.popstack.mvoter2015.R
import com.popstack.mvoter2015.databinding.ItemNewsBinding
import com.popstack.mvoter2015.domain.news.model.NewsId
import com.popstack.mvoter2015.helper.diff.diffCallBackWith
import com.popstack.mvoter2015.helper.extensions.inflater
import com.popstack.mvoter2015.helper.extensions.withSafeAdapterPosition

class NewsRecyclerViewAdapter(
  val onNewsClick: (@ParameterName("id") NewsId, @ParameterName("url") String) -> Unit
) : PagingDataAdapter<NewsViewItem, NewsRecyclerViewAdapter.NewsViewHolder>(
  diffCallBackWith(areItemTheSame = { item1, item2 -> item1.id == item2.id },
    areContentsTheSame = { item1, item2 -> item1 == item2 })
) {

  class NewsViewHolder(val binding: ItemNewsBinding) : RecyclerView.ViewHolder(binding.root)

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
    val binding = ItemNewsBinding.inflate(parent.inflater(), parent, false)
    val holder = NewsViewHolder(binding)
    holder.itemView.setOnClickListener {
      holder.withSafeAdapterPosition { position ->
        getItem(position)?.let { itemAtIndex ->
          onNewsClick.invoke(itemAtIndex.id, itemAtIndex.url)
        }
      }
    }
    return holder
  }

  override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
    getItem(position)?.let { itemAtIndex ->
      holder.binding.apply {
        tvTitle.text = itemAtIndex.title
        tvSummary.text = itemAtIndex.summary
        tvPublishedDate.text = itemAtIndex.publishedDate
        ivPreview.load(itemAtIndex.imageUrl) {
          placeholder(R.drawable.placeholder_rect)
          error(R.drawable.placeholder_rect)
          crossfade(true)
        }
      }
    }
  }
}