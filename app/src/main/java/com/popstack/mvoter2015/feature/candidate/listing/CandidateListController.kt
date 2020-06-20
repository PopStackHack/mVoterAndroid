package com.popstack.mvoter2015.feature.candidate.listing

import android.view.LayoutInflater
import android.view.View
import androidx.lifecycle.Observer
import com.popstack.mvoter2015.core.mvp.MvvmController
import com.popstack.mvoter2015.databinding.ControllerCandidateListBinding
import com.popstack.mvoter2015.helper.conductor.requireActivity
import com.popstack.mvoter2015.helper.livedata.nonNull

internal class CandidateListController :
  MvvmController<ControllerCandidateListBinding>() {

  private val viewModel: CandidateListViewModel by viewModels()

  private val pagerAdapter by lazy {
    CandidateListHousePagerAdapter(this)
  }

  override val bindingInflater: (LayoutInflater) -> ControllerCandidateListBinding =
    ControllerCandidateListBinding::inflate

  override fun onBindView() {
    super.onBindView()

    binding.viewPager.adapter = pagerAdapter
    binding.tabLayout.setupWithViewPager(binding.viewPager)

    CandidateListPagerParentRouter.setParentRouter(router)

    viewModel.houseViewItemListLiveData.nonNull()
      .observe(lifecycleOwner, Observer(::observeHouseViewItem))
    viewModel.loadHouses()
  }

  private fun observeHouseViewItem(houseViewItemList: List<CandidateListHouseViewItem>) {
    pagerAdapter.setItems(houseViewItemList)
  }

  override fun onDestroyView(view: View) {
    if (requireActivity().isChangingConfigurations) {
      binding.viewPager.adapter = null
    }

    binding.tabLayout.setupWithViewPager(null)
    super.onDestroyView(view)
  }

  override fun onDestroy() {
    CandidateListPagerParentRouter.destroy()
    super.onDestroy()
  }

}