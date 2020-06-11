package com.popstack.mvoter2015.feature.home

import android.view.LayoutInflater
import com.bluelinelabs.conductor.RouterTransaction
import com.popstack.mvoter2015.R
import com.popstack.mvoter2015.core.mvp.MvvmController
import com.popstack.mvoter2015.databinding.ControllerHomeBinding
import com.popstack.mvoter2015.feature.candidate.listing.CandidateListController
import com.popstack.mvoter2015.feature.howtovote.HowToVoteController
import com.popstack.mvoter2015.feature.info.InfoController
import com.popstack.mvoter2015.feature.party.listing.PartyListController
import com.popstack.mvoter2015.feature.voteresult.VoteResultController
import com.popstack.mvoter2015.helper.conductor.BottomNavigationRouterMediator

class HomeController : MvvmController<ControllerHomeBinding>() {

  private val viewModel: HomeViewModel by viewModels()

  override val bindingInflater: (LayoutInflater) -> ControllerHomeBinding =
    ControllerHomeBinding::inflate

  private val bottomNavigationRouterMediator by lazy {
    BottomNavigationRouterMediator(
      this,
      binding.container,
      binding.bottomNavigationView,
      mapOf(
        R.id.navigation_candidate to { RouterTransaction.with(CandidateListController()) },
        R.id.navigation_party to { RouterTransaction.with(PartyListController()) },
        R.id.navigation_how_to_vote to { RouterTransaction.with(HowToVoteController()) },
        R.id.navigation_info to { RouterTransaction.with(InfoController()) },
        R.id.navigation_vote_result to { RouterTransaction.with(VoteResultController()) }
      )
    )
  }

  override fun onBindView() {
    bottomNavigationRouterMediator.attach()
    binding.bottomNavigationView.selectedItemId = R.id.navigation_candidate
  }

}