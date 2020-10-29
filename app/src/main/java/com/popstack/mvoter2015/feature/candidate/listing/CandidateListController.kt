package com.popstack.mvoter2015.feature.candidate.listing

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.viewpager.widget.ViewPager
import com.bluelinelabs.conductor.RouterTransaction
import com.google.android.material.tabs.TabLayout
import com.popstack.mvoter2015.R
import com.popstack.mvoter2015.core.mvp.MvvmController
import com.popstack.mvoter2015.databinding.ControllerCandidateListBinding
import com.popstack.mvoter2015.feature.HasRouter
import com.popstack.mvoter2015.feature.analytics.screen.ScreenTrackAnalyticsProvider
import com.popstack.mvoter2015.feature.candidate.search.CandidateSearchController
import com.popstack.mvoter2015.feature.location.LocationUpdateController
import com.popstack.mvoter2015.helper.ConstituencyTab
import com.popstack.mvoter2015.helper.asyncviewstate.AsyncViewState
import com.popstack.mvoter2015.helper.conductor.requireActivity
import com.popstack.mvoter2015.helper.conductor.requireContext
import com.popstack.mvoter2015.helper.conductor.setSupportActionBar
import com.popstack.mvoter2015.helper.conductor.supportActionBar
import com.popstack.mvoter2015.helper.extensions.justify
import com.popstack.mvoter2015.logging.HasTag
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

class CandidateListController :
  MvvmController<ControllerCandidateListBinding>(), HasTag {

  override val tag: String = CONTROLLER_TAG

  companion object {
    const val CONTROLLER_TAG = "CandidateListController"
    const val VIEW_STATE_SELECTED_TAB = "view_selected_tab"
  }

  private val viewModel: CandidateListViewModel by viewModels(
    store = viewModelStore
  )

  private val pagerAdapter by lazy {
    CandidateListHousePagerAdapter(this)
  }

  override val bindingInflater: (LayoutInflater) -> ControllerCandidateListBinding =
    ControllerCandidateListBinding::inflate

  private var selectedTab: Int? = null

  override fun onBindView(savedViewState: Bundle?) {
    super.onBindView(savedViewState)

    selectedTab = savedViewState?.getInt(VIEW_STATE_SELECTED_TAB)

    setSupportActionBar(binding.toolBar)
    supportActionBar()?.title = requireContext().getString(R.string.title_candidates)

    setHasOptionsMenu(R.menu.menu_candidate) { menuItem ->
      when (menuItem.itemId) {
        R.id.action_change_location -> {
          if (requireActivity() is HasRouter) {
            (requireActivity() as HasRouter).router()
              .pushController(RouterTransaction.with(LocationUpdateController()))
          }
          true
        }
        R.id.action_search -> {
          router.pushController(RouterTransaction.with(CandidateSearchController()))
          true
        }
      }
      false
    }

    /**
     * In case we need to change to user's selected tab,
     * we need to hide the list before changing the tab
     * to avoid glitching experience
     */
    hideCandidateList()

    binding.viewPager.offscreenPageLimit = 3
    binding.viewPager.adapter = pagerAdapter
    binding.viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
      override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
        //DO NOTHING
      }

      override fun onPageSelected(position: Int) {
        val canTrackScreen = when (position) {
          0 -> CandidateListViewPagerTrackScreen("LowerHouseCandidateListController")
          1 -> CandidateListViewPagerTrackScreen("UpperHouseCandidateListController")
          2 -> CandidateListViewPagerTrackScreen("RegionalHouseCandidateListController")
          else -> throw IllegalStateException()
        }
        ScreenTrackAnalyticsProvider.screenTackAnalytics(requireContext())
          .trackScreen(canTrackScreen)
      }

      override fun onPageScrollStateChanged(state: Int) {
        //DO NOTHING
      }

    })
    binding.tabLayout.setupWithViewPager(binding.viewPager)

    binding.btnChoose.setOnClickListener {
      if (requireActivity() is HasRouter) {
        (requireActivity() as HasRouter).router()
          .pushController(RouterTransaction.with(LocationUpdateController()))
      }
    }

    setupTabLayout()

    CandidateListPagerParentRouter.setParentRouter(router)

    viewModel.houseViewItemListResultLiveData.observe(this, Observer(::observeHouseViewItemListResult))

    if (viewModel.houseViewItemListResultLiveData.value == null) {
      viewModel.loadHouses()
    }
  }

  private fun observeHouseViewItemListResult(viewState: AsyncViewState<CandidateListViewModel.HouseViewItemListResult>) {
    if (viewState is AsyncViewState.Loading) binding.progressIndicator.show()
    else binding.progressIndicator.hide()

    if (viewState is AsyncViewState.Success) {
      val result = viewState.value
      binding.tabLayout.isVisible = result is CandidateListViewModel.HouseViewItemListResult.HouseViewItemList
      binding.viewPager.isVisible = result is CandidateListViewModel.HouseViewItemListResult.HouseViewItemList
      binding.groupChooseCandidateComponent.isVisible = result is CandidateListViewModel.HouseViewItemListResult.RequestUserLocation
      if (result is CandidateListViewModel.HouseViewItemListResult.HouseViewItemList) {
        showCandidatePrivacyInstructionIfNeeded()
        pagerAdapter.setItems(result.itemList)
        binding.tabLayout.removeAllTabs()
        result.itemList.forEach {
          binding.tabLayout.addTab(
            binding.tabLayout.newTab().setCustomView(
              ConstituencyTab(requireActivity()).apply {
                setText(it.houseName)
              }
            )
          )
        }
        changeSelectedTabIfNeeded()
      }
    }
  }

  @Inject
  lateinit var viewCache: CandidateListViewCache

  @SuppressLint("WrongConstant")
  private fun showCandidatePrivacyInstructionIfNeeded() {
    binding.tvCandidatePrivacyInstruction.justify()

    lifecycleScope.launch {
      viewCache.shouldShowCandidatePrivacyInstruction().collectLatest {
        binding.layoutCandidatePrivacyInstruction.isVisible = it
      }
    }

    binding.ivCloseCandidatePrivacyInstruction.setOnClickListener {
      lifecycleScope.launch {
        viewCache.setShouldShowCandidatePrivacyInstruction(false)
      }
    }
  }

  private fun setupTabLayout() {
    binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
      override fun onTabReselected(tab: TabLayout.Tab?) {}

      override fun onTabUnselected(tab: TabLayout.Tab?) {
        (tab?.customView as? ConstituencyTab)?.setUnselected()
      }

      override fun onTabSelected(tab: TabLayout.Tab?) {
        (tab?.customView as? ConstituencyTab)?.setSelected()
      }
    })
  }

  private fun showCandidateList() {
    binding.tabLayout.isVisible = true
    binding.viewPager.isVisible = true
  }

  private fun hideCandidateList() {
    binding.tabLayout.isVisible = false
    binding.viewPager.isVisible = false
  }

  private fun changeSelectedTabIfNeeded() {
    selectedTab?.let {
      binding.viewPager.post {
        binding.viewPager.setCurrentItem(it, false)
        showCandidateList()
      }
    } ?: showCandidateList()
  }

  override fun onSaveViewState(view: View, outState: Bundle) {
    outState.putInt(VIEW_STATE_SELECTED_TAB, binding.viewPager.currentItem)
    super.onSaveViewState(view, outState)
  }

  override fun onDestroyView(view: View) {
    binding.viewPager.adapter = null
    binding.tabLayout.setupWithViewPager(null)
    super.onDestroyView(view)
  }

  override fun onDestroy() {
    CandidateListPagerParentRouter.destroy()
    super.onDestroy()
  }

}