package com.popstack.mvoter2015.feature.party.listing

import android.view.LayoutInflater
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.popstack.mvoter2015.R
import com.popstack.mvoter2015.core.mvp.MvpFragment
import com.popstack.mvoter2015.databinding.FragmentPartyListBinding
import com.popstack.mvoter2015.domain.party.model.PartyId
import com.popstack.mvoter2015.feature.party.listing.PartyListViewItemRecyclerViewAdapter.PartyListItemClickListener
import com.popstack.mvoter2015.helper.RecyclerViewMarginDecoration

class PartyListFragment : MvpFragment<FragmentPartyListBinding, PartyListView, PartyListViewModel>(),
  PartyListView, PartyListItemClickListener {

  override val viewModel: PartyListViewModel by contractedViewModels()

  override val bindingInflater: (LayoutInflater) -> ViewBinding =
    FragmentPartyListBinding::inflate

  private val partyListAdapter by lazy {
    PartyListViewItemRecyclerViewAdapter(this)
  }

  override fun onBindView() {
    super.onBindView()

    binding.rvParty.apply {
      adapter = partyListAdapter
      layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
      val dimen =
        requireContext().resources.getDimensionPixelSize(R.dimen.recycler_view_item_margin)
      addItemDecoration(RecyclerViewMarginDecoration(dimen, 0))
    }
  }

  override fun onItemClick(partyId: PartyId) {
    val goToDetailDirection =
      PartyListFragmentDirections.goToPartyDetail(partyId.value)
    findNavController().navigate(goToDetailDirection)
  }

  override fun subscribeToViewItemLiveData(viewItemLiveData: LiveData<List<PartyListViewItem>>) {
    viewItemLiveData.observe(viewLifecycleOwner, Observer { partyListViewItemList ->
      partyListAdapter.submitList(partyListViewItemList)
    })
  }

}