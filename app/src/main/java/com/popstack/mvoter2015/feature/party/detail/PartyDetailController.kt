package com.popstack.mvoter2015.feature.party.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import coil.api.load
import coil.size.Scale
import com.popstack.mvoter2015.R
import com.popstack.mvoter2015.core.mvp.MvvmController
import com.popstack.mvoter2015.databinding.ControllerPartyDetailBinding
import com.popstack.mvoter2015.domain.party.model.PartyId
import com.popstack.mvoter2015.domain.utils.convertToBurmeseNumber
import com.popstack.mvoter2015.helper.asyncviewstate.AsyncViewState
import com.popstack.mvoter2015.helper.conductor.requireActivityAsAppCompatActivity
import com.popstack.mvoter2015.helper.conductor.requireContext
import com.popstack.mvoter2015.helper.intent.Intents
import com.popstack.mvoter2015.logging.HasTag

class PartyDetailController(bundle: Bundle) : MvvmController<ControllerPartyDetailBinding>(bundle), HasTag {

  override val tag: String = "PartyDetailController"

  /***
   * Since we dont have factory yet
   * https://github.com/bluelinelabs/Conductor/pull/594
   */
  companion object {
    private const val ARG_PARTY_ID = "party_id"

    fun newInstance(partyId: PartyId): PartyDetailController {
      return PartyDetailController(
        bundleOf(
          ARG_PARTY_ID to partyId.value
        )
      )
    }
  }

  private val viewModel: PartyDetailViewModel by viewModels()

  private val timelineAdapter by lazy {
    PartyTimelineRecyclerViewAdapter()
  }

  override val bindingInflater: (LayoutInflater) -> ControllerPartyDetailBinding =
    ControllerPartyDetailBinding::inflate

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup, savedViewState: Bundle?): View {
    viewModel.setPartyId(PartyId(args.getString(ARG_PARTY_ID)!!))
    return super.onCreateView(inflater, container, savedViewState)
  }

  override fun onBindView(savedViewState: Bundle?) {
    super.onBindView(savedViewState)

    binding.rvTimeline.apply {
      adapter = timelineAdapter
      layoutManager = LinearLayoutManager(requireContext())
    }
    requireActivityAsAppCompatActivity().setSupportActionBar(binding.toolBar)
    requireActivityAsAppCompatActivity().supportActionBar?.title = ""
    binding.toolBar.setNavigationIcon(R.drawable.ic_nav_on_primary_bg)

    viewModel.viewItemLiveData.observe(this, Observer(::observeViewItem))
    binding.btnRetry.setOnClickListener {
      viewModel.loadData()
    }

    if (savedViewState == null) {
      viewModel.loadData()
    }
  }

  private fun observeViewItem(viewState: AsyncViewState<PartyDetailViewItem>) {
    binding.progressBar.isVisible = viewState is AsyncViewState.Loading
    binding.layoutContent.isVisible = viewState is AsyncViewState.Success
    binding.tvErrorMessage.isVisible = viewState is AsyncViewState.Error
    binding.btnRetry.isVisible = viewState is AsyncViewState.Error

    when (viewState) {
      is AsyncViewState.Success -> {
        val viewItem = viewState.value
        binding.ivPartySeal.load(viewItem.sealImage) {
          scale(Scale.FIT)
          placeholder(R.drawable.placeholder_rect)
          error(R.drawable.placeholder_rect)
          crossfade(true)
        }
        binding.tvPartyName.text = viewItem.name

        binding.tvPartyNameEnglish.isVisible = viewItem.nameEnglish != null
        viewItem.nameEnglish?.let {
          binding.tvPartyNameEnglish.text = it
        }

        binding.tvPartyNameAbbreviation.isVisible = viewItem.nameAbbreviation != null
        viewItem.nameAbbreviation?.let {
          binding.tvPartyNameAbbreviation.text = it
        }

        binding.buttonPolicy.setOnClickListener {
          Intents.viewUrl(viewItem.policy)
        }

        binding.ivPartyFlag.load(viewItem.flagImage) {
          scale(Scale.FIT)
          placeholder(R.drawable.placeholder_rect)
          error(R.drawable.placeholder_rect)
          crossfade(true)
        }

        binding.tvPartyNumber.text = viewItem.partyNumber.convertToBurmeseNumber()

        binding.tvLeader.text = viewItem.leadersAndChairmen
        binding.tvMemberCount.text = viewItem.memberCount
        binding.tvHeadquarterLocation.text = viewItem.headQuarterLocation
        binding.tvContact.text = viewItem.contact
        binding.cardViewTimeline.isVisible = viewItem.timeline.isNotEmpty()

        binding.tvPoteMa25.isVisible = viewItem.isPoteMa25
        timelineAdapter.submitList(viewItem.timeline)
      }
      is AsyncViewState.Error -> {
        val error = viewState.errorMessage
        binding.tvErrorMessage.text = error
      }
    }
  }
}