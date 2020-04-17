package com.popstack.mvoter2015.feature.candidate.listing

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.viewbinding.ViewBinding
import com.google.android.material.tabs.TabLayoutMediator
import com.popstack.mvoter2015.core.mvp.MvpFragment
import com.popstack.mvoter2015.databinding.FragmentCandidateListBinding

internal class CandidateListFragment :
  MvpFragment<FragmentCandidateListBinding, CandidateListView, CandidateListViewModel>(),
  CandidateListView {

  override val viewModel: CandidateListViewModel by contractedViewModels()

  override val bindingInflater: (LayoutInflater) -> ViewBinding =
    FragmentCandidateListBinding::inflate

  override fun onBindView() {
    super.onBindView()

//    setHasOptionsMenu(true)
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    viewModel.loadHouses()
  }

  override fun setUpHouse(viewItems: List<CandidateListHouseViewItem>) {

    //Not global val due to this issue
    //https://issuetracker.google.com/issues/140507668

    val pagerAdapter = CandidateListHousePagerAdapter(parentFragmentManager, lifecycle)
    pagerAdapter.setItems(viewItems)
    binding.viewPager.adapter = pagerAdapter
    TabLayoutMediator(
      binding.tabLayout,
      binding.viewPager,
      true
    ) { tab, position ->
      tab.text = pagerAdapter.getTitleAtPosition(position)
    }.attach()
  }

  override fun onDestroyView() {
//    tabLayoutMediator.detach()
    super.onDestroyView()
  }

}