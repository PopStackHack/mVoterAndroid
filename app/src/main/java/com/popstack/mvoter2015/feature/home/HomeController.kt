package com.popstack.mvoter2015.feature.home

import android.view.LayoutInflater
import android.view.ViewGroup
import com.bluelinelabs.conductor.RouterTransaction
import com.popstack.mvoter2015.R
import com.popstack.mvoter2015.core.mvp.MvpController
import com.popstack.mvoter2015.databinding.ControllerHomeBinding
import com.popstack.mvoter2015.di.conductor.InjectionControllerChangeListener
import com.popstack.mvoter2015.feature.candidate.listing.CandidateListController
import com.popstack.mvoter2015.feature.howtovote.HowToVoteController
import com.popstack.mvoter2015.feature.info.InfoController
import com.popstack.mvoter2015.feature.party.listing.PartyListController
import com.popstack.mvoter2015.feature.voteresult.VoteResultController
import com.popstack.mvoter2015.helper.conductor.BottomNavigationRouterMediator

class HomeController : MvpController<ControllerHomeBinding, HomeView, HomeViewModel>(), HomeView {

  override val viewModel: HomeViewModel by contractedViewModels()

  override val bindingInflater: (LayoutInflater, ViewGroup) -> ControllerHomeBinding
    get() = { layoutInflater, viewGroup ->
      ControllerHomeBinding.inflate(layoutInflater, viewGroup, false)
    }

  protected val injectionControllerChangeListener by lazy {
    InjectionControllerChangeListener()
  }

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
    bottomNavigationRouterMediator.setup()

    childRouters.forEach { childRouter ->
      childRouter.addChangeListener(injectionControllerChangeListener)
    }

    binding.bottomNavigationView.selectedItemId = R.id.navigation_candidate
  }

  override fun onDestroy() {
    childRouters.forEach { childRouter ->
      childRouter.removeChangeListener(injectionControllerChangeListener)
    }
    super.onDestroy()
  }

}