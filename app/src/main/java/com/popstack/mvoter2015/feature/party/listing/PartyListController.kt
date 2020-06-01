package com.popstack.mvoter2015.feature.party.listing

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.popstack.mvoter2015.R
import com.popstack.mvoter2015.core.mvp.MvpController
import com.popstack.mvoter2015.databinding.ControllerPartyListBinding
import com.popstack.mvoter2015.domain.party.model.PartyId
import com.popstack.mvoter2015.feature.party.listing.PartyListViewItemRecyclerViewAdapter.PartyListItemClickListener
import com.popstack.mvoter2015.helper.RecyclerViewMarginDecoration

class PartyListController : MvpController<ControllerPartyListBinding, PartyListView, PartyListViewModel>(),
  PartyListView, PartyListItemClickListener {

  override val viewModel: PartyListViewModel by contractedViewModels()

  override val bindingInflater: (LayoutInflater, ViewGroup) -> ControllerPartyListBinding
    get() = { layoutInflater, viewGroup ->
      ControllerPartyListBinding.inflate(layoutInflater, viewGroup, false)
    }

  private val partyListAdapter by lazy {
    PartyListViewItemRecyclerViewAdapter(this)
  }

  override fun onBindView() {
    super.onBindView()

    binding.rvParty.apply {
      adapter = partyListAdapter
      layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
      val dimen =
        context.resources.getDimensionPixelSize(R.dimen.recycler_view_item_margin)
      addItemDecoration(RecyclerViewMarginDecoration(dimen, 0))
    }
  }

  override fun onItemClick(partyId: PartyId) {
//    val goToDetailDirection =
//      PartyListFragmentDirections.goToPartyDetail(partyId.value)
//    findNavController().navigate(goToDetailDirection)
  }

  override fun subscribeToViewItemLiveData(viewItemLiveData: LiveData<List<PartyListViewItem>>) {
    viewItemLiveData.observe(lifecycleOwner, Observer { partyListViewItemList ->
      partyListAdapter.submitList(partyListViewItemList)
    })
  }

}