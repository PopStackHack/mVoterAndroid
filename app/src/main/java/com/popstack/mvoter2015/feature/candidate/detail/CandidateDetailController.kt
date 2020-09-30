package com.popstack.mvoter2015.feature.candidate.detail

import android.content.Intent
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.core.widget.NestedScrollView
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import coil.load
import coil.size.Scale
import coil.transform.CircleCropTransformation
import com.bluelinelabs.conductor.RouterTransaction
import com.popstack.mvoter2015.R
import com.popstack.mvoter2015.core.mvp.MvvmController
import com.popstack.mvoter2015.databinding.ControllerCandidateDetailBinding
import com.popstack.mvoter2015.domain.candidate.model.CandidateId
import com.popstack.mvoter2015.feature.analytics.screen.CanTrackScreen
import com.popstack.mvoter2015.feature.candidate.listing.CandidateListRecyclerViewAdapter
import com.popstack.mvoter2015.feature.image.FullScreenImageViewActivity
import com.popstack.mvoter2015.feature.party.PartySharedElementTransitionChangeHandler
import com.popstack.mvoter2015.feature.party.detail.PartyDetailController
import com.popstack.mvoter2015.feature.share.ShareUrlFactory
import com.popstack.mvoter2015.helper.RecyclerViewMarginDecoration
import com.popstack.mvoter2015.helper.asyncviewstate.AsyncViewState
import com.popstack.mvoter2015.helper.conductor.requireActivity
import com.popstack.mvoter2015.helper.conductor.requireActivityAsAppCompatActivity
import com.popstack.mvoter2015.helper.conductor.requireContext
import com.popstack.mvoter2015.helper.conductor.supportActionBar
import com.popstack.mvoter2015.helper.intent.Intents
import com.popstack.mvoter2015.helper.spannable.CenteredImageSpan
import com.popstack.mvoter2015.logging.HasTag

class CandidateDetailController(
  bundle: Bundle? = null
) : MvvmController<ControllerCandidateDetailBinding>(bundle), HasTag, CanTrackScreen {

  override val tag: String = "CandidateDetailController"

  override val screenName: String = "CandidateDetailController"

  /***
   * Since we dont have factory yet
   * https://github.com/bluelinelabs/Conductor/pull/594
   */
  companion object {
    private const val ARG_CANDIDATE_ID = "candidate_id"

    fun newInstance(candidateId: CandidateId): CandidateDetailController {
      return CandidateDetailController(
        bundleOf(
          ARG_CANDIDATE_ID to candidateId.value
        )
      )
    }
  }

  private val viewModel: CandidateDetailViewModel by viewModels()

  override val bindingInflater: (LayoutInflater) -> ControllerCandidateDetailBinding =
    ControllerCandidateDetailBinding::inflate

  private val candidateId by lazy {
    CandidateId(args.getString(ARG_CANDIDATE_ID)!!)
  }

  private val candidateListAdapter by lazy {
    CandidateListRecyclerViewAdapter(onCandidateClicked)
  }

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup,
    savedViewState: Bundle?
  ): View {
    setHasOptionsMenu(R.menu.menu_candidate_detail, this::handleMenuItemClick)
    return super.onCreateView(inflater, container, savedViewState)
  }

  private fun handleMenuItemClick(menuItem: MenuItem): Boolean {
    when (menuItem.itemId) {
      R.id.action_share -> {
        val shareCandidateIntent = Intents.shareUrl(ShareUrlFactory().candidate(candidateId))
        startActivity(Intent.createChooser(shareCandidateIntent, "Share Candidate..."))
        return true
      }
    }
    return false
  }

  private val onCandidateClicked: (CandidateId) -> Unit = {
    val candidateDetailsController = newInstance(it)
    router.pushController(RouterTransaction.with(candidateDetailsController))
  }

  override fun onBindView(savedViewState: Bundle?) {
    super.onBindView(savedViewState)

    requireActivityAsAppCompatActivity().setSupportActionBar(binding.toolBar)
    requireActivityAsAppCompatActivity().supportActionBar?.title = ""
    supportActionBar()?.setDisplayHomeAsUpEnabled(true)

    binding.svCandidateInfo.setOnScrollChangeListener(
      NestedScrollView.OnScrollChangeListener { _, _, scrollY, _, _ ->
        val activity = kotlin.runCatching {
          requireActivity()
        }.getOrNull() ?: return@OnScrollChangeListener
        var color: Int = ContextCompat.getColor(activity, R.color.primary)
        var textColor: Int = ContextCompat.getColor(activity, R.color.text_primary)
        if (scrollY < 256) {
          val alpha = scrollY shl 24 or (-1 ushr 8)
          color = color and alpha
          textColor = textColor and alpha
        }
        binding.toolBar.setTitleTextColor(textColor)
        binding.toolBar.setBackgroundColor(color)
      }
    )

    binding.rvRivalCandidates.apply {
      adapter = candidateListAdapter
      layoutManager = LinearLayoutManager(requireContext())
      val dimen = context.resources.getDimensionPixelSize(R.dimen.recycler_view_item_margin)
      addItemDecoration(RecyclerViewMarginDecoration(dimen, 0))
    }

    viewModel.viewItemLiveData.observe(this, Observer(::observeViewItem))

    binding.btnRetry.setOnClickListener {
      viewModel.loadCandidate(candidateId)
    }

    if (viewModel.viewItemLiveData.value == null) {
      viewModel.loadCandidate(candidateId)
    }

  }

  private fun observeViewItem(viewState: AsyncViewState<CandidateDetailsViewItem>) {
    binding.progressBar.isVisible = viewState is AsyncViewState.Loading
    binding.svCandidateInfo.isVisible = viewState is AsyncViewState.Success
    binding.tvErrorMessage.isVisible = viewState is AsyncViewState.Error
    binding.btnRetry.isVisible = viewState is AsyncViewState.Error

    when (viewState) {
      is AsyncViewState.Success -> {
        with(viewState.value.candidateInfo) {
          binding.toolBar.title = name
          binding.tvCandidateName.text = name

          if (partyId == null) {
            binding.tvCandidatePartyName.text = partyName
            binding.tvCandidatePartyName.setOnClickListener(null)
          } else {
            val partyNameSpan = "$partyName " // empty space is to put the arrow icon
            val partyNameWithRightArrow = SpannableStringBuilder(partyNameSpan)
            partyNameWithRightArrow.setSpan(CenteredImageSpan(requireActivity(), R.drawable.ic_arrow_right_text_primary_24), partyNameSpan.length - 1, partyNameSpan.length, Spannable.SPAN_INCLUSIVE_INCLUSIVE)
            binding.tvCandidatePartyName.text = partyNameWithRightArrow
            binding.tvCandidatePartyName.setOnClickListener {
              val partyDetailController = PartyDetailController.newInstance(
                partyId = partyId,
                partyName = partyName,
              )
              router.pushController(
                RouterTransaction.with(partyDetailController)
                  .pushChangeHandler(PartySharedElementTransitionChangeHandler())
                  .popChangeHandler(PartySharedElementTransitionChangeHandler())
              )
            }
          }

          binding.tvConstituencyName.text = constituencyName
          binding.tvAge.text = age
          binding.tvBirthday.text = birthday
          binding.tvEducation.text = education
          binding.tvWork.text = job
          binding.tvRace.text = ethnicity
          binding.tvReligion.text = religion
          binding.tvMotherName.text = motherName
          binding.tvMotherEthnicity.text = motherEthnicity
          binding.tvMotherReligion.text = motherReligion
          binding.tvFatherName.text = fatherName
          binding.tvFatherEthnicity.text = fatherEthnicity
          binding.tvFatherReligion.text = fatherReligion
          binding.groupElectedBadge.isVisible = isElected

          binding.ivCandidatePartySeal.load(partySealImageUrl) {
            placeholder(R.drawable.party_seal_placeholder_rect)
            error(R.drawable.party_seal_placeholder_rect)
            scale(Scale.FIT)
          }
          partySealImageUrl?.let { imageUrl ->
            binding.ivCandidatePartySeal.setOnClickListener {
              val imageViewerIntent = FullScreenImageViewActivity.intent(requireContext(), imageUrl)
              startActivity(imageViewerIntent)
            }
          }

          binding.ivCandidate.load(photo) {
            transformations(CircleCropTransformation())
            scale(Scale.FILL)
            placeholder(R.drawable.placeholder_oval)
            error(R.drawable.placeholder_oval)
          }

          binding.ivCandidate.setOnClickListener {
            val imageViewerIntent = FullScreenImageViewActivity.intent(requireContext(), photo)
            startActivity(imageViewerIntent)
          }
        }
        candidateListAdapter.submitList(viewState.value.rivals)
      }
      is AsyncViewState.Error -> {
        val error = viewState.errorMessage
        binding.tvErrorMessage.text = error
      }
    }
  }
}