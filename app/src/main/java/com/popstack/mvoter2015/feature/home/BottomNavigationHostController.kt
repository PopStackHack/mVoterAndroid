package com.popstack.mvoter2015.feature.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.bluelinelabs.conductor.RouterTransaction
import com.popstack.mvoter2015.R
import com.popstack.mvoter2015.core.LifeCycleAwareController
import com.popstack.mvoter2015.databinding.ControllerBottomNavHostBinding
import com.popstack.mvoter2015.di.Injectable
import com.popstack.mvoter2015.domain.location.usecase.GetUserSelectedLocation
import com.popstack.mvoter2015.feature.analytics.location.SelectedLocationAnalytics
import com.popstack.mvoter2015.feature.candidate.listing.CandidateListController
import com.popstack.mvoter2015.feature.faq.FaqController
import com.popstack.mvoter2015.feature.news.NewsController
import com.popstack.mvoter2015.feature.party.listing.PartyListController
import com.popstack.mvoter2015.feature.votingguide.VotingGuideController
import com.popstack.mvoter2015.helper.conductor.BNVRouterPagerAdapter
import com.popstack.mvoter2015.logging.HasTag
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

class BottomNavigationHostController : LifeCycleAwareController<ControllerBottomNavHostBinding>(), HasTag, Injectable {

  override val tag: String = TAG

  companion object {
    const val TAG = "BottomNavigationHostController"
  }

  private val viewModelStore = ViewModelStore()

  override val bindingInflater: (LayoutInflater) -> ControllerBottomNavHostBinding =
    ControllerBottomNavHostBinding::inflate

  @Inject
  lateinit var getUserSelectedLocation: GetUserSelectedLocation

  @Inject
  lateinit var selectedLocationAnalytics: SelectedLocationAnalytics

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup,
    savedViewState: Bundle?
  ): View {
    BottomNavigationHostViewModelStore.viewModelStore = viewModelStore
    val view = super.onCreateView(inflater, container, savedViewState)

    if (savedViewState == null) {
      binding.bottomNavigationView.selectedItemId = R.id.navigation_candidate
    }
    return view
  }

  override fun onBindView(savedViewState: Bundle?) {

    val bottomNavRouterPagerAdapter = BNVRouterPagerAdapter(
      this,
      binding.bottomNavigationView,
      binding.homeViewPager,
      mapOf(
        R.id.navigation_candidate to { RouterTransaction.with(CandidateListController()).tag(CandidateListController.CONTROLLER_TAG) },
        R.id.navigation_party to {
          RouterTransaction.with(
            PartyListController()
          )
        },
        R.id.navigation_how_to_vote to {
          RouterTransaction.with(VotingGuideController())
        },
        R.id.navigation_info to {
          RouterTransaction.with(FaqController())
        },
        R.id.navigation_news to {
          RouterTransaction.with(NewsController())
        }
      )
    )
    binding.homeViewPager.adapter = bottomNavRouterPagerAdapter

    binding.bottomNavigationView.setOnNavigationItemReselectedListener { menuItem ->
      when (menuItem.itemId) {
        R.id.navigation_candidate -> {
          bottomNavRouterPagerAdapter.getRouter(0)?.popToTag(CandidateListController.CONTROLLER_TAG)
        }
        R.id.navigation_party -> {
          kotlin.runCatching {
            view?.findViewById<RecyclerView>(R.id.rvParty)?.smoothScrollToPosition(0)
          }
        }
        R.id.navigation_info -> {
          kotlin.runCatching {
            view?.findViewById<RecyclerView>(R.id.rvFaq)?.smoothScrollToPosition(0)
          }
        }
        R.id.navigation_news -> {
          kotlin.runCatching {
            view?.findViewById<RecyclerView>(R.id.rvNews)?.smoothScrollToPosition(0)
          }
        }
      }
    }

    lifecycleScope.launch {
      getUserSelectedLocation.execute(Unit).collectLatest { combinedLocation ->
        if (combinedLocation != null) {
          selectedLocationAnalytics.logLocation(combinedLocation)
        }
      }
    }
  }

  override fun onDestroyView(view: View) {
    viewModelStore.clear()
    BottomNavigationHostViewModelStore.viewModelStore = null
    super.onDestroyView(view)
  }

}