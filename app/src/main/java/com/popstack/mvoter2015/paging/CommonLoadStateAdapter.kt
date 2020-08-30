package com.popstack.mvoter2015.paging

import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.popstack.mvoter2015.databinding.FooterPagerLoadStateBinding
import com.popstack.mvoter2015.exception.GlobalExceptionHandler
import com.popstack.mvoter2015.helper.extensions.inflater

/**
 * A common loading state adapter to be used across app for consistency
 * @see https://developer.android.com/topic/libraries/architecture/paging/v3-paged-data#display-loading-state
 */
class CommonLoadStateAdapter(
  private val retry: () -> Unit
) : LoadStateAdapter<CommonLoadStateAdapter.LoadStateViewHolder>() {

  override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): LoadStateViewHolder {
    val binding = FooterPagerLoadStateBinding.inflate(parent.inflater(), parent, false)
    val holder = LoadStateViewHolder(binding)
    holder.apply {
      binding.buttonRetry.setOnClickListener {
        retry.invoke()
      }
    }
    return holder
  }

  override fun onBindViewHolder(holder: LoadStateViewHolder, loadState: LoadState) {

    holder.binding.apply {
      if (loadState is LoadState.Error) {
        tvErrorMessage.text = GlobalExceptionHandler(holder.itemView.context).getMessageForUser(loadState.error)
      }

      progressBar.isVisible = loadState is LoadState.Loading
      buttonRetry.isVisible = loadState is LoadState.Error
      tvErrorMessage.isVisible = loadState is LoadState.Error
    }
  }

  class LoadStateViewHolder(
    val binding: FooterPagerLoadStateBinding
  ) : RecyclerView.ViewHolder(binding.root)

}