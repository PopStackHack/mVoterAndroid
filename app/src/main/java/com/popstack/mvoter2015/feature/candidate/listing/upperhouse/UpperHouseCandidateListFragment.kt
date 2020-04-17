package com.popstack.mvoter2015.feature.candidate.listing.upperhouse

import android.view.LayoutInflater
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.popstack.mvoter2015.R
import com.popstack.mvoter2015.core.mvp.MvpFragment
import com.popstack.mvoter2015.databinding.FragmentUpperHouseCandidateListBinding
import com.popstack.mvoter2015.domain.candidate.model.CandidateId
import com.popstack.mvoter2015.feature.candidate.listing.CandidateListFragmentDirections
import com.popstack.mvoter2015.feature.candidate.listing.upperhouse.UpperHouseCandidateListRecyclerViewAdapter.UpperHouseCandidateListItemClickListener
import com.popstack.mvoter2015.helper.RecyclerViewMarginDecoration

class UpperHouseCandidateListFragment :
  MvpFragment<FragmentUpperHouseCandidateListBinding, UpperHouseCandidateListView, UpperHouseCandidateListViewModel>(),
  UpperHouseCandidateListView, UpperHouseCandidateListItemClickListener {

  override val viewModel: UpperHouseCandidateListViewModel by contractedViewModels()

  override val bindingInflater: (LayoutInflater) -> ViewBinding =
    FragmentUpperHouseCandidateListBinding::inflate

  private val candidateListAdapter by lazy {
    UpperHouseCandidateListRecyclerViewAdapter(this)
  }

  override fun onBindView() {
    super.onBindView()

    binding.rvUpperHouseCandidate.apply {
      adapter = candidateListAdapter
      layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
      val dimen =
        requireContext().resources.getDimensionPixelSize(R.dimen.recycler_view_item_margin)
      addItemDecoration(RecyclerViewMarginDecoration(dimen, 0))
    }

  }

  override fun subscribeToViewItemLiveData(viewItemLiveData: LiveData<List<UpperHouseCandidateListViewItem>>) {
    viewItemLiveData.observe(viewLifecycleOwner, Observer { viewItemList ->
      candidateListAdapter.submitList(viewItemList)
    })

  }

  override fun onItemClick(candidateId: CandidateId) {
    val goDetailDirection = CandidateListFragmentDirections.goDetailAction(
      candidateId = candidateId.value
    )
    findNavController().navigate(goDetailDirection)
  }

}