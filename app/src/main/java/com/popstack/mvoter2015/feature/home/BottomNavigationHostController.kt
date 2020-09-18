package com.popstack.mvoter2015.feature.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelStore
import com.bluelinelabs.conductor.RouterTransaction
import com.popstack.mvoter2015.R
import com.popstack.mvoter2015.core.BaseController
import com.popstack.mvoter2015.databinding.ControllerBottomNavHostBinding
import com.popstack.mvoter2015.feature.candidate.listing.CandidateListController
import com.popstack.mvoter2015.feature.faq.FaqController
import com.popstack.mvoter2015.feature.news.NewsController
import com.popstack.mvoter2015.feature.party.listing.PartyListController
import com.popstack.mvoter2015.feature.votingguide.VotingGuideController
import com.popstack.mvoter2015.helper.conductor.BNVRouterPagerAdapter
import com.popstack.mvoter2015.logging.HasTag

class BottomNavigationHostController : BaseController<ControllerBottomNavHostBinding>(), HasTag {

  override val tag: String = TAG

  companion object {
    const val TAG = "BottomNavigationHostController"
  }

  private val viewModelStore = ViewModelStore()

  override val bindingInflater: (LayoutInflater) -> ControllerBottomNavHostBinding =
    ControllerBottomNavHostBinding::inflate

  private var newsNewsNavigationItemReselectedCallback: NewsNavigationItemReselectedCallback? = null

  private var partyNavigationItemReselectedCallback: PartyNavigationItemReselectedCallback? = null

  private var faqNavigationItemReselectedCallback: FaqNavigationItemReselectedCallback? = null

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
            PartyListController().also {
              partyNavigationItemReselectedCallback = it
            }
          )
        },
        R.id.navigation_how_to_vote to { RouterTransaction.with(VotingGuideController()) },
        R.id.navigation_info to {
          RouterTransaction.with(
            FaqController().also {
              faqNavigationItemReselectedCallback = it
            }
          )
        },
        R.id.navigation_news to {
          RouterTransaction.with(
            NewsController().also {
              newsNewsNavigationItemReselectedCallback = it
            }
          )
        }
      )
    )
    binding.homeViewPager.adapter = bottomNavRouterPagerAdapter

    binding.bottomNavigationView.setOnNavigationItemReselectedListener { menuItem ->
      when (menuItem.itemId) {
        R.id.navigation_candidate -> {
          bottomNavRouterPagerAdapter.getRouter(0)?.popToTag(CandidateListController.CONTROLLER_TAG)
        }
        R.id.navigation_party -> partyNavigationItemReselectedCallback?.onPartyNavigationItemReselected()
        R.id.navigation_info -> faqNavigationItemReselectedCallback?.onFaqNavigationItemReselected()
        R.id.navigation_news -> newsNewsNavigationItemReselectedCallback?.onNewsNavigationItemReselected()
      }
    }
  }

  override fun onDestroyView(view: View) {
    viewModelStore.clear()
    BottomNavigationHostViewModelStore.viewModelStore = null
    super.onDestroyView(view)
  }

  override fun onDestroy() {
    newsNewsNavigationItemReselectedCallback = null
    partyNavigationItemReselectedCallback = null
    super.onDestroy()
  }

}