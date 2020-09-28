package com.popstack.mvoter2015.feature.location

import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.popstack.mvoter2015.R
import com.popstack.mvoter2015.core.recyclerview.ViewBindingViewHolder
import com.popstack.mvoter2015.databinding.ItemStateRegionTownshipBinding
import com.popstack.mvoter2015.helper.diff.diffCallBackWith
import com.popstack.mvoter2015.helper.extensions.inflater

internal class StateRegionTownshipRecyclerViewAdapter constructor(
  private val onStateRegionClick: (Int, String) -> Unit,
  private val onTownshipClick: (String) -> Unit,
  private val onRetryClick: () -> Unit
) : ListAdapter<StateRegionTownshipViewItem, StateRegionTownshipRecyclerViewAdapter.StateRegionTownshipViewItemViewHolder>(
  diffCallBackWith(
    areItemTheSame = { item1, item2 ->
      item1.name == item2.name
    },
    areContentsTheSame = { item1, item2 ->
      item1 == item2
    }
  )
) {

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StateRegionTownshipViewItemViewHolder {
    return StateRegionTownshipViewItemViewHolder(
      ItemStateRegionTownshipBinding.inflate(parent.inflater(), parent, false),
      onStateRegionClick,
      onTownshipClick,
      onRetryClick
    )
  }

  override fun onBindViewHolder(holder: StateRegionTownshipViewItemViewHolder, position: Int) {
    holder.bind(getItem(position))
  }

  class StateRegionTownshipViewItemViewHolder(
    binding: ItemStateRegionTownshipBinding,
    onStateRegionClick: (Int, String) -> Unit,
    onTownshipClick: (String) -> Unit,
    onRetryClick: () -> Unit
  ) : ViewBindingViewHolder<ItemStateRegionTownshipBinding>(binding) {

    private lateinit var stateRegionTownshipViewItem: StateRegionTownshipViewItem

    private val townshipAdapter = TownshipRecyclerViewAdapter(onTownshipClick)

    init {
      binding.tvStateRegion.setOnClickListener {
        onStateRegionClick(absoluteAdapterPosition, stateRegionTownshipViewItem.name)
      }

      binding.ivDropDownArrow.setOnClickListener {
        onStateRegionClick(absoluteAdapterPosition, stateRegionTownshipViewItem.name)
      }

      binding.btnRetry.setOnClickListener {
        onRetryClick()
      }

      binding.rvTownship.apply {
        adapter = townshipAdapter
        layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
      }
    }

    fun bind(stateRegionTownshipViewItem: StateRegionTownshipViewItem) {
      this.stateRegionTownshipViewItem = stateRegionTownshipViewItem

      binding.tvStateRegion.text = stateRegionTownshipViewItem.name

      with(stateRegionTownshipViewItem) {
        if (isSelected) {
          binding.ivDropDownArrow.setImageDrawable(itemView.context.getDrawable(R.drawable.ic_baseline_keyboard_arrow_down_24))

          val shouldShowTownships = townshipList.isNotEmpty()
          val shouldShowError = error.isNotEmpty()

          changeVisibility(
            townshipListIsVisible = shouldShowTownships,
            errorComponentIsVisible = shouldShowError,
            progressBarIsVisible = isLoading && error.isEmpty()
          )

          if (shouldShowTownships) {
            townshipAdapter.submitList(townshipList)
            return
          }

          if (shouldShowError) {
            binding.tvErrorMessage.text = error
          }
        } else {
          binding.ivDropDownArrow.setImageDrawable(itemView.context.getDrawable(R.drawable.ic_arrow_right_24))
          changeVisibility()
        }
      }
    }

    private fun changeVisibility(
      townshipListIsVisible: Boolean = false,
      errorComponentIsVisible: Boolean = false,
      progressBarIsVisible: Boolean = false
    ) = with(binding) {
      rvTownship.isVisible = townshipListIsVisible
      groupErrorComponent.isVisible = errorComponentIsVisible
      progressBar.isVisible = progressBarIsVisible
    }
  }
}