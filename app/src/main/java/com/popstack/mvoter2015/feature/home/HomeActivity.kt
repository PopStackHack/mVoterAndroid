package com.popstack.mvoter2015.feature.home

import android.os.Bundle
import androidx.navigation.findNavController
import com.popstack.mvoter2015.R
import com.popstack.mvoter2015.core.mvp.MvpActivity
import com.popstack.mvoter2015.databinding.ActivityHomeBinding
import com.popstack.mvoter2015.helper.extensions.setupWithNavController

class HomeActivity : MvpActivity<ActivityHomeBinding, HomeView, HomeViewModel>(), HomeView {

  override val viewModel: HomeViewModel by contractedViewModels()

  override val binding: ActivityHomeBinding by lazy {
    ActivityHomeBinding.inflate(layoutInflater)
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    binding.bottomNavigationView.setupWithNavController(
        navGraphIds = listOf(
            R.navigation.navigation_candidate,
            R.navigation.navigation_party,
            R.navigation.navigation_how_to_vote,
            R.navigation.navigation_info,
            R.navigation.navigation_vote_result
        ),
        fragmentManager = supportFragmentManager,
        containerId = R.id.navFragment,
        intent = intent
    )

  }

  override fun onSupportNavigateUp(): Boolean =
    findNavController(R.id.nav_host_fragment_container).navigateUp()

}