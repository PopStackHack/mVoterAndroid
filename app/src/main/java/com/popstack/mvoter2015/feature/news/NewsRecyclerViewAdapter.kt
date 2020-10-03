package com.popstack.mvoter2015.feature.news

import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.popstack.mvoter2015.R
import com.popstack.mvoter2015.databinding.ItemNewsNoPreviewBinding
import com.popstack.mvoter2015.databinding.ItemNewsWithPreviewBinding
import com.popstack.mvoter2015.domain.news.model.NewsId
import com.popstack.mvoter2015.helper.diff.diffCallBackWith
import com.popstack.mvoter2015.helper.extensions.inflater
import com.popstack.mvoter2015.helper.extensions.withSafeAdapterPosition

class NewsRecyclerViewAdapter(
  val onNewsClick: (@ParameterName("id") NewsId, @ParameterName("url") String) -> Unit
) : PagingDataAdapter<NewsViewItem, NewsRecyclerViewAdapter.NewsViewHolder>(
  diffCallBackWith(
    areItemTheSame = { item1, item2 -> item1.id == item2.id },
    areContentsTheSame = { item1, item2 -> item1 == item2 }
  )
) {

  companion object {
    private const val VIEW_TYPE_NO_PREVIEW = 1
    private const val VIEW_TYPE_WITH_PREVIEW = 2
  }

  sealed class NewsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    class NewsNoPreviewViewHolder(val binding: ItemNewsNoPreviewBinding) :
      NewsViewHolder(binding.root)

    class NewsWithPreviewViewHolder(val binding: ItemNewsWithPreviewBinding) :
      NewsViewHolder(binding.root)

  }

  override fun getItemViewType(position: Int): Int {
    val itemAtIndex = getItem(position)
    if (itemAtIndex?.imageUrl == null) {
      return VIEW_TYPE_NO_PREVIEW
    }

    return VIEW_TYPE_WITH_PREVIEW
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
    when (viewType) {
      VIEW_TYPE_NO_PREVIEW -> {
        val binding = ItemNewsNoPreviewBinding.inflate(parent.inflater(), parent, false)
        val holder = NewsViewHolder.NewsNoPreviewViewHolder(binding)
        holder.itemView.setOnClickListener {
          holder.withSafeAdapterPosition { position ->
            getItem(position)?.let { itemAtIndex ->
              onNewsClick.invoke(itemAtIndex.id, itemAtIndex.url)
            }
          }
        }
        return holder
      }
      VIEW_TYPE_WITH_PREVIEW -> {
        val binding = ItemNewsWithPreviewBinding.inflate(parent.inflater(), parent, false)
        val holder = NewsViewHolder.NewsWithPreviewViewHolder(binding)
        holder.itemView.setOnClickListener {
          holder.withSafeAdapterPosition { position ->
            getItem(position)?.let { itemAtIndex ->
              onNewsClick.invoke(itemAtIndex.id, itemAtIndex.url)
            }
          }
        }
        return holder
      }
      else -> {
        throw IllegalStateException()
      }
    }

  }

  override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
    getItem(position)?.let { itemAtIndex ->
      when (holder) {
        is NewsViewHolder.NewsNoPreviewViewHolder -> {
          holder.binding.apply {
            tvTitle.text = itemAtIndex.title
            tvSummary.text = itemAtIndex.summary
            tvPublishedDate.text = itemAtIndex.publishedDate
          }
        }
        is NewsViewHolder.NewsWithPreviewViewHolder -> {
          holder.binding.apply {
            tvTitle.text = itemAtIndex.title
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
  }
}