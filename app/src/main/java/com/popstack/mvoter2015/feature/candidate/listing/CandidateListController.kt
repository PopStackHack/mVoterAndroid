package com.popstack.mvoter2015.feature.candidate.listing

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.viewpager.widget.ViewPager
import com.bluelinelabs.conductor.RouterTransaction
import com.popstack.mvoter2015.R
import com.popstack.mvoter2015.core.mvp.MvvmController
import com.popstack.mvoter2015.databinding.ControllerCandidateListBinding
import com.popstack.mvoter2015.feature.HasRouter
import com.popstack.mvoter2015.feature.analytics.screen.ScreenTrackAnalyticsProvider
import com.popstack.mvoter2015.feature.candidate.search.CandidateSearchController
import com.popstack.mvoter2015.feature.location.LocationUpdateController
import com.popstack.mvoter2015.helper.conductor.requireActivity
import com.popstack.mvoter2015.helper.conductor.requireContext
import com.popstack.mvoter2015.helper.conductor.setSupportActionBar
import com.popstack.mvoter2015.helper.conductor.supportActionBar
import com.popstack.mvoter2015.logging.HasTag

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

    viewModel.viewEventLiveData.observe(this, Observer(::observeViewEvent))

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

    CandidateListPagerParentRouter.setParentRouter(router)

    viewModel.houseViewItemListLiveData.observe(lifecycleOwner, Observer(::observeHouseViewItem))
    viewModel.loadHouses()
  }

  private fun showCandidateList() {
    binding.tabLayout.isVisible = true
    binding.viewPager.isVisible = true
  }

  private fun hideCandidateList() {
    binding.tabLayout.isVisible = false
    binding.viewPager.isVisible = false
  }

  private fun observeViewEvent(viewEvent: CandidateListViewModel.ViewEvent) {
    if (viewEvent is CandidateListViewModel.ViewEvent.RequestUserLocation) {
      binding.tabLayout.isVisible = false
      binding.groupChooseCandidateComponent.isVisible = true
    }
  }

  private fun observeHouseViewItem(houseViewItemList: List<CandidateListHouseViewItem>) {
    binding.groupChooseCandidateComponent.isVisible = false
    pagerAdapter.setItems(houseViewItemList)
    changeSelectedTabIfNeeded()
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