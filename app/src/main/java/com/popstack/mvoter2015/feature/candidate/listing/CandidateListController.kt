package com.popstack.mvoter2015.feature.candidate.listing

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.popstack.mvoter2015.core.mvp.MvpController
import com.popstack.mvoter2015.databinding.ControllerCandidateListBinding
import com.popstack.mvoter2015.helper.conductor.requireActivity

internal class CandidateListController :
  MvpController<ControllerCandidateListBinding, CandidateListView, CandidateListViewModel>(),
  CandidateListView {

  override val viewModel: CandidateListViewModel by contractedViewModels()

  override val bindingInflater: (LayoutInflater, ViewGroup) -> ControllerCandidateListBinding
    get() = { layoutInflater, viewGroup ->
      ControllerCandidateListBinding.inflate(layoutInflater)
    }

  private val pagerAdapter by lazy {
    CandidateListHousePagerAdapter(this)
  }

  override fun onBindView() {
    super.onBindView()

    binding.viewPager.adapter = pagerAdapter
    binding.tabLayout.setupWithViewPager(binding.viewPager)

    CandidateListPagerParentRouter.setParentRouter(router)
    viewModel.loadHouses()
  }

  override fun setUpHouse(viewItems: List<CandidateListHouseViewItem>) {
    pagerAdapter.setItems(viewItems)
  }

  override fun onDestroyView(view: View) {
    if (requireActivity().isChangingConfigurations) {
      binding.viewPager.adapter = null
    }

    binding.tabLayout.setupWithViewPager(null)
    CandidateListPagerParentRouter.destroy()
    super.onDestroyView(view)
  }

}