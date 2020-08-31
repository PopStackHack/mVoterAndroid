package com.popstack.mvoter2015.feature.location

import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.popstack.mvoter2015.core.recyclerview.ViewBindingViewHolder
import com.popstack.mvoter2015.databinding.ItemStateRegionTownshipBinding
import com.popstack.mvoter2015.helper.diff.diffCallBackWith
import com.popstack.mvoter2015.helper.extensions.inflater
import timber.log.Timber

internal class StateRegionTownshipRecyclerViewAdapter constructor(
  private val onStateRegionClick: (String) -> Unit,
  private val onTownshipClick: (String) -> Unit,
  private val onRetryClick: () -> Unit
) : ListAdapter<StateRegionTownshipViewItem, StateRegionTownshipRecyclerViewAdapter.StateRegionTownshipViewItemViewHolder>(
  diffCallBackWith(
    areItemTheSame = { item1, item2 ->
      item1.name == item2.name
    },
    areContentsTheSame = { item1, item2 ->
      item1.name == item2.name &&
        item1.isLoading == item2.isLoading &&
        item1.isSelected == item2.isSelected &&
        item1.townshipList.size == item2.townshipList.size &&
        item1.error == item2.error
    }
  )
) {

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StateRegionTownshipViewItemViewHolder {
    return StateRegionTownshipViewItemViewHolder(
      ItemStateRegionTownshipBinding.inflate(parent.inflater(), parent, false),
      onStateRegionClick,
      onTownshipClick,
      onRetryClick)
  }

  override fun onBindViewHolder(holder: StateRegionTownshipViewItemViewHolder, position: Int) {
    holder.bind(getItem(position))
  }

  class StateRegionTownshipViewItemViewHolder(
    binding: ItemStateRegionTownshipBinding,
    onStateRegionClick: (String) -> Unit,
    onTownshipClick: (String) -> Unit,
    onRetryClick: () -> Unit
  ) : ViewBindingViewHolder<ItemStateRegionTownshipBinding>(binding) {

    private lateinit var stateRegionTownshipViewItem: StateRegionTownshipViewItem

    private val townshipAdapter = TownshipRecyclerViewAdapter(onTownshipClick)

    init {
      binding.tvStateRegion.setOnClickListener {
        onStateRegionClick(stateRegionTownshipViewItem.name)
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

      with (stateRegionTownshipViewItem) {
        binding.groupDropDown.isVisible = isSelected

        if (isSelected) {
          binding.progressBar.isVisible = isLoading && error.isEmpty()
          val shouldShowTownships = townshipList.isNotEmpty()
          binding.rvTownship.isVisible = shouldShowTownships

          if (shouldShowTownships) {
            townshipAdapter.submitList(townshipList)
          }

          val shouldShowError = error.isNotEmpty()
          binding.tvErrorMessage.isVisible = shouldShowError
          binding.btnRetry.isVisible = shouldShowError
          binding.tvErrorMessage.text = error
        }
      }
    }
  }
}