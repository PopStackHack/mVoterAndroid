package com.popstack.mvoter2015.feature.candidate.detail

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import coil.api.load
import com.bluelinelabs.conductor.RouterTransaction
import com.popstack.mvoter2015.R
import com.popstack.mvoter2015.core.mvp.MvvmController
import com.popstack.mvoter2015.databinding.ControllerCandidateDetailBinding
import com.popstack.mvoter2015.domain.candidate.model.CandidateId
import com.popstack.mvoter2015.feature.candidate.listing.CandidateListRecyclerViewAdapter
import com.popstack.mvoter2015.feature.party.PartySharedElementTransitionChangeHandler
import com.popstack.mvoter2015.feature.party.detail.PartyDetailController
import com.popstack.mvoter2015.feature.share.ShareUrlFactory
import com.popstack.mvoter2015.helper.asyncviewstate.AsyncViewState
import com.popstack.mvoter2015.helper.conductor.requireActivityAsAppCompatActivity
import com.popstack.mvoter2015.helper.conductor.requireContext
import com.popstack.mvoter2015.helper.conductor.supportActionBar
import com.popstack.mvoter2015.helper.intent.Intents
import com.popstack.mvoter2015.logging.HasTag

class CandidateDetailController(
  bundle: Bundle? = null
) : MvvmController<ControllerCandidateDetailBinding>(bundle), HasTag {

  override val tag: String = "CandidateDetailController"

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

    binding.rvRivalCandidates.apply {
      adapter = candidateListAdapter
      layoutManager = LinearLayoutManager(requireContext())
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
          binding.tvCandidateName.text = name
          binding.tvCandidatePartyName.text = partyName

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

          binding.tvConstituencyType.text = houseType
          binding.tvConstituencyName.text = constituencyName
          binding.tvAge.text = age
          binding.tvBirthday.text = birthday
          binding.tvEducation.text = education
          binding.tvWork.text = job
          binding.tvRaceReligion.text = religion
          binding.tvMotherName.text = motherName
          binding.tvMotherReligion.text = motherReligion
          binding.tvFatherName.text = fatherName
          binding.tvFatherReligion.text = fatherReligion
          binding.ivCandidatePartySeal.load(partySealImageUrl) {
            placeholder(R.drawable.placeholder_rect)
            error(R.drawable.placeholder_rect)
            crossfade(true)
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