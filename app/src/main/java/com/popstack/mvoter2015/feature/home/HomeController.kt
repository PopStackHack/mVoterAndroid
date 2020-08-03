package com.popstack.mvoter2015.feature.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.popstack.mvoter2015.R
import com.popstack.mvoter2015.core.mvp.MvvmController
import com.popstack.mvoter2015.databinding.ControllerHomeBinding
import com.popstack.mvoter2015.feature.candidate.listing.CandidateListController
import com.popstack.mvoter2015.feature.faq.FaqController
import com.popstack.mvoter2015.feature.howtovote.HowToVoteController
import com.popstack.mvoter2015.feature.news.NewsController
import com.popstack.mvoter2015.feature.party.listing.PartyListController
import com.popstack.mvoter2015.helper.conductor.BNVRouterPagerAdapter

class HomeController : MvvmController<ControllerHomeBinding>() {

  companion object {
    const val TAG = "HomeController"
  }

  private val viewModel: HomeViewModel by viewModels()

  override val bindingInflater: (LayoutInflater) -> ControllerHomeBinding =
    ControllerHomeBinding::inflate

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup,
    savedViewState: Bundle?
  ): View {
    val view = super.onCreateView(inflater, container, savedViewState)

    if (savedViewState == null) {
      binding.bottomNavigationView.selectedItemId = R.id.navigation_candidate
    }
    return view
  }

  override fun onBindView(savedViewState: Bundle?) {
    binding.homeViewPager.adapter =
      BNVRouterPagerAdapter(
        this,
        binding.bottomNavigationView,
        binding.homeViewPager,
        mapOf(
          R.id.navigation_candidate to { CandidateListController() },
          R.id.navigation_party to { PartyListController() },
          R.id.navigation_how_to_vote to { HowToVoteController() },
          R.id.navigation_info to { FaqController() },
          R.id.navigation_news to { NewsController() }
        )
      )
  }

}