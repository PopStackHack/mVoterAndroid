package com.popstack.mvoter2015.feature.faq.ballot

import android.os.Bundle
import android.view.LayoutInflater
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.popstack.mvoter2015.R
import com.popstack.mvoter2015.core.mvp.MvvmController
import com.popstack.mvoter2015.databinding.ControllerBallotExampleBinding
import com.popstack.mvoter2015.domain.faq.model.BallotExampleCategory
import com.popstack.mvoter2015.domain.utils.convertToBurmeseNumber
import com.popstack.mvoter2015.feature.analytics.screen.CanTrackScreen
import com.popstack.mvoter2015.feature.faq.displayString
import com.popstack.mvoter2015.feature.home.BottomNavigationHostViewModelStore
import com.popstack.mvoter2015.feature.image.FullScreenImageViewActivity
import com.popstack.mvoter2015.helper.asyncviewstate.AsyncViewState
import com.popstack.mvoter2015.helper.conductor.requireActivity
import com.popstack.mvoter2015.helper.conductor.requireActivityAsAppCompatActivity
import com.popstack.mvoter2015.helper.conductor.requireContext
import com.popstack.mvoter2015.helper.conductor.setSupportActionBar
import com.popstack.mvoter2015.helper.conductor.supportActionBar
import com.popstack.mvoter2015.helper.extensions.addOnTabSelectedListener
import com.popstack.mvoter2015.helper.extensions.forEachTab
import com.popstack.mvoter2015.logging.HasTag
import timber.log.Timber

class BallotExampleController : MvvmController<ControllerBallotExampleBinding>(), HasTag, CanTrackScreen {

  override val tag: String = "BallotExampleController"

  override val screenName: String = "BallotExampleController"

  override val bindingInflater: (LayoutInflater) -> ControllerBallotExampleBinding =
    ControllerBallotExampleBinding::inflate

  private val viewModel: BallotExampleViewModel by viewModels(
    store = BottomNavigationHostViewModelStore.viewModelStore ?: viewModelStore
  )

  private val ballotAdapter by lazy {
    BallotExampleRecyclerViewAdapter(
      onImageClick = { _, imageUrl ->
        val imageViewerIntent = FullScreenImageViewActivity.intent(requireContext(), imageUrl)
        startActivity(imageViewerIntent)
      }
    )
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
      icon?.setTint(ContextCompat.getColor(requireActivity(), R.color.grey))
    }
  }

  val validBallotTab by lazy {
    binding.tabLayoutValid.newTab().apply {
      setIcon(R.drawable.ic_check_circle_24)
      setText(R.string.valid_ballot)
      icon?.setTint(ContextCompat.getColor(requireActivity(), R.color.grey))
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
        binding.viewPager.setCurrentItem(pagerPosition, true)
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
    private var previousScrollState = 0
    private var scrollState = 0
    private var lastPositionOffset = 0f

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

      binding.ivScrollLeft.isVisible = position != 0
      binding.ivScrollRight.isVisible = position != ballotAdapter.itemCount - 1

      binding.tvPageIndicator.text = "${position + 1} / ${ballotAdapter.itemCount}".convertToBurmeseNumber()
    }

    override fun onPageScrollStateChanged(state: Int) {
      previousScrollState = scrollState
      scrollState = state
    }

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

      if (position == viewModel.validBallotStartPosition - 1 && positionOffset != 0.0f) {

        val updateText = scrollState != ViewPager2.SCROLL_STATE_SETTLING || previousScrollState == ViewPager2.SCROLL_STATE_DRAGGING
        val updateIndicator = !(scrollState == ViewPager2.SCROLL_STATE_SETTLING && previousScrollState == ViewPager2.SCROLL_STATE_IDLE)

        Timber.i("position $position, validStartIndex : ${viewModel.validBallotStartPosition}, offset: $positionOffset")
        binding.tabLayoutValid.setScrollPosition(0, positionOffset, updateText, updateIndicator)

        lastPositionOffset = positionOffset
      }
      super.onPageScrolled(position, positionOffset, positionOffsetPixels)
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

    //TODO: Add debounce?
    binding.ivScrollRight.setOnClickListener {
      binding.viewPager.setCurrentItem(binding.viewPager.currentItem + 1, true)
    }
    binding.ivScrollLeft.setOnClickListener {
      binding.viewPager.setCurrentItem(binding.viewPager.currentItem - 1, true)
    }

    setUpTabLayout()
    binding.viewPager.registerOnPageChangeCallback(onPageChangeTabSelect)
    binding.tabLayoutValid.addOnTabSelectedListener(onTabSelectToChangePagerPosition)

    binding.btnRetry.setOnClickListener {
      viewModel.selectBallotExampleCategory(
        viewModel.selectedBallotExampleCategory() ?: BallotExampleCategory.NORMAL
      )
    }

    viewModel.ballotViewItemLiveData.observe(
      this,
      { viewState ->
        binding.viewPager.isVisible = viewState is AsyncViewState.Success
        binding.tabLayoutValid.isVisible = viewState is AsyncViewState.Success
        binding.progressIndicator.isVisible = viewState is AsyncViewState.Loading
        binding.tvErrorMessage.isVisible = viewState is AsyncViewState.Error
        binding.btnRetry.isVisible = viewState is AsyncViewState.Error

        if (viewState is AsyncViewState.Success) {
          ballotAdapter.submitList(viewState.value)
        } else if (viewState is AsyncViewState.Error) {
          binding.tvErrorMessage.text = viewState.errorMessage
        }
      }
    )

    viewModel.ballotExampleCategoryLiveData.observe(
      lifecycleOwner,
      { ballotExampleCategory ->
        binding.tvSelectedCategory.text = ballotExampleCategory.displayString(requireContext())
      }
    )

    viewModel.selectBallotExampleCategory(
      viewModel.selectedBallotExampleCategory() ?: BallotExampleCategory.NORMAL
    )
  }

  private fun setUpTabLayout() {
    binding.tabLayoutValid.addTab(inValidBallotTab)
    binding.tabLayoutValid.addTab(validBallotTab)

    val onTabSelect = { tab: TabLayout.Tab? ->
      if (tab == inValidBallotTab) {
        tab.icon?.setTint(ContextCompat.getColor(requireActivity(), R.color.text_error))
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