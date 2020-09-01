package com.popstack.mvoter2015.feature.faq.ballot

import android.os.Bundle
import android.view.LayoutInflater
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.popstack.mvoter2015.R
import com.popstack.mvoter2015.core.mvp.MvvmController
import com.popstack.mvoter2015.databinding.ControllerBallotExampleBinding
import com.popstack.mvoter2015.domain.faq.model.BallotExampleCategory
import com.popstack.mvoter2015.feature.faq.displayString
import com.popstack.mvoter2015.feature.home.BottomNavigationHostViewModelStore
import com.popstack.mvoter2015.helper.asyncviewstate.AsyncViewState
import com.popstack.mvoter2015.helper.conductor.requireActivityAsAppCompatActivity
import com.popstack.mvoter2015.helper.conductor.requireContext
import com.popstack.mvoter2015.helper.conductor.setSupportActionBar
import com.popstack.mvoter2015.helper.conductor.supportActionBar
import com.popstack.mvoter2015.helper.extensions.addOnTabSelectedListener
import com.popstack.mvoter2015.helper.extensions.forEachTab
import com.popstack.mvoter2015.logging.HasTag

class BallotExampleController : MvvmController<ControllerBallotExampleBinding>(), HasTag {

  override val tag: String = "BallotExampleController"

  override val bindingInflater: (LayoutInflater) -> ControllerBallotExampleBinding =
    ControllerBallotExampleBinding::inflate

  private val viewModel: BallotExampleViewModel by viewModels(
    store = BottomNavigationHostViewModelStore.viewModelStore ?: viewModelStore
  )

  private val ballotAdapter by lazy {
    BallotExampleRecyclerViewAdapter()
  }

  private val selectBallotExampleCategoryContract by lazy {
    requireActivityAsAppCompatActivity().registerForActivityResult(
      BallotExampleSelectActivity.SelectBallotCategoryContract()
    ) { selectedCategory ->
      if (selectedCategory != null) {
        viewModel.selectBallotExampleCategory(selectedCategory)
      }
    }
  }

  private val inValidBallotTab by lazy {
    binding.tabLayoutValid.newTab().apply {
      setIcon(R.drawable.ic_close_circle_24)
      setText(R.string.invalid_ballot)
      icon?.setTint(ContextCompat.getColor(requireContext(), R.color.grey))
    }
  }

  val validBallotTab by lazy {
    binding.tabLayoutValid.newTab().apply {
      setIcon(R.drawable.ic_check_circle_24)
      setText(R.string.valid_ballot)
      icon?.setTint(ContextCompat.getColor(requireContext(), R.color.grey))
    }
  }

  private val onTabSelectToChangePagerPosition: TabLayout.OnTabSelectedListener = object : TabLayout.OnTabSelectedListener {
    override fun onTabSelected(tab: TabLayout.Tab?) {
      val pagerPosition = when (tab) {
        validBallotTab -> viewModel.validBallotStartPosition
        inValidBallotTab -> viewModel.invalidBallotStartPosition
        else -> viewModel.invalidBallotStartPosition
      }
      if (binding.viewPager.currentItem != pagerPosition) {
        binding.viewPager.unregisterOnPageChangeCallback(onPageChangeTabSelect)
        binding.viewPager.setCurrentItem(pagerPosition, true)
        binding.viewPager.registerOnPageChangeCallback(onPageChangeTabSelect)
      }
    }

    override fun onTabUnselected(tab: TabLayout.Tab?) {
      //DO NOTHING
    }

    override fun onTabReselected(tab: TabLayout.Tab?) {
      //DO NOTHING
    }

  }

  private val onPageChangeTabSelect = object : ViewPager2.OnPageChangeCallback() {
    override fun onPageSelected(position: Int) {
      val tabToBeSelected = if (position >= viewModel.invalidBallotStartPosition && position < viewModel.validBallotStartPosition) {
        inValidBallotTab
      } else if (position >= viewModel.validBallotStartPosition) {
        validBallotTab
      } else {
        inValidBallotTab
      }

      if (tabToBeSelected != binding.tabLayoutValid.getTabAt(binding.tabLayoutValid.selectedTabPosition)) {
        binding.tabLayoutValid.removeOnTabSelectedListener(onTabSelectToChangePagerPosition)
        binding.tabLayoutValid.selectTab(tabToBeSelected)
        binding.tabLayoutValid.addOnTabSelectedListener(onTabSelectToChangePagerPosition)
      }
    }
  }

  override fun onBindView(savedViewState: Bundle?) {
    super.onBindView(savedViewState)

    setSupportActionBar(binding.toolBar)
    supportActionBar()?.title = ""
    supportActionBar()?.setDisplayHomeAsUpEnabled(true)

    binding.tvSelectedCategory.setOnClickListener {
      viewModel.selectedBallotExampleCategory()?.let {
        selectBallotExampleCategoryContract.launch(it)
      }
    }

    binding.viewPager.apply {
      adapter = ballotAdapter
    }

    setUpTabLayout()

    binding.viewPager.registerOnPageChangeCallback(onPageChangeTabSelect)
    binding.tabLayoutValid.addOnTabSelectedListener(onTabSelectToChangePagerPosition)

    binding.btnRetry.setOnClickListener {
      viewModel.selectBallotExampleCategory(viewModel.selectedBallotExampleCategory()
        ?: BallotExampleCategory.NORMAL)
    }

    viewModel.ballotViewItemLiveData.observe(this, Observer { viewState ->
      binding.viewPager.isVisible = viewState is AsyncViewState.Success
      binding.tabLayoutValid.isVisible = viewState is AsyncViewState.Success
      binding.progressBar.isVisible = viewState is AsyncViewState.Loading
      binding.tvErrorMessage.isVisible = viewState is AsyncViewState.Error
      binding.btnRetry.isVisible = viewState is AsyncViewState.Error

      if (viewState is AsyncViewState.Success) {
        ballotAdapter.submitList(viewState.value)
      } else if (viewState is AsyncViewState.Error) {
        binding.tvErrorMessage.text = viewState.errorMessage
      }
    })

    viewModel.ballotExampleCategoryLiveData.observe(lifecycleOwner, Observer { ballotExampleCategory ->
      binding.tvSelectedCategory.text = ballotExampleCategory.displayString(requireContext())
    })

    viewModel.selectBallotExampleCategory(viewModel.selectedBallotExampleCategory()
      ?: BallotExampleCategory.NORMAL)
  }

  private fun setUpTabLayout() {
    binding.tabLayoutValid.addTab(inValidBallotTab)
    binding.tabLayoutValid.addTab(validBallotTab)

    val onTabSelect = { tab: TabLayout.Tab? ->
      if (tab == inValidBallotTab) {
        tab.icon?.setTint(ContextCompat.getColor(requireContext(), R.color.text_error))
      } else if (tab == validBallotTab) {
        tab.icon?.setTint(ContextCompat.getColor(requireContext(), R.color.green))
      }

      binding.tabLayoutValid.forEachTab {
        if (it != tab) {
          it.icon?.setTint(ContextCompat.getColor(requireContext(), R.color.grey))
        }
      }
    }

    binding.tabLayoutValid.addOnTabSelectedListener(
      onTabReselected = onTabSelect,
      onTabSelected = onTabSelect
    )

    binding.tabLayoutValid.selectTab(inValidBallotTab)
  }
}