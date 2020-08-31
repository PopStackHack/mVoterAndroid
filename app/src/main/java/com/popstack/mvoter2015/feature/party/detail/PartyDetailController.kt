package com.popstack.mvoter2015.feature.party.detail

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import coil.api.load
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.popstack.mvoter2015.R
import com.popstack.mvoter2015.core.mvp.MvvmController
import com.popstack.mvoter2015.databinding.ControllerPartyDetailBinding
import com.popstack.mvoter2015.domain.party.model.PartyId
import com.popstack.mvoter2015.domain.utils.convertToBurmeseNumber
import com.popstack.mvoter2015.feature.share.ShareUrlFactory
import com.popstack.mvoter2015.helper.ViewVisibilityDebounceHandler
import com.popstack.mvoter2015.helper.asyncviewstate.AsyncViewState
import com.popstack.mvoter2015.helper.conductor.requireActivity
import com.popstack.mvoter2015.helper.conductor.requireActivityAsAppCompatActivity
import com.popstack.mvoter2015.helper.conductor.requireContext
import com.popstack.mvoter2015.helper.conductor.supportActionBar
import com.popstack.mvoter2015.helper.format
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

  private val partyId by lazy {
    PartyId(args.getString(ARG_PARTY_ID)!!)
  }

  private val viewModel: PartyDetailViewModel by viewModels()

  private val timelineAdapter by lazy {
    PartyTimelineRecyclerViewAdapter()
  }

  override val bindingInflater: (LayoutInflater) -> ControllerPartyDetailBinding =
    ControllerPartyDetailBinding::inflate

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup, savedViewState: Bundle?): View {
    viewModel.setPartyId(partyId)
    setHasOptionsMenu(R.menu.menu_party_detail, this::handleMenuItemClick)
    return super.onCreateView(inflater, container, savedViewState)
  }

  private fun handleMenuItemClick(menuItem: MenuItem): Boolean {
    when (menuItem.itemId) {
      R.id.action_share -> {
        val sharePartyIntent = Intents.shareUrl(ShareUrlFactory().party(partyId))
        startActivity(Intent.createChooser(sharePartyIntent, "Share Party"))
        return true
      }
    }
    return false
  }

  override fun onBindView(savedViewState: Bundle?) {
    super.onBindView(savedViewState)

    binding.rvTimeline.apply {
      adapter = timelineAdapter
      layoutManager = LinearLayoutManager(requireContext())
    }
    requireActivityAsAppCompatActivity().setSupportActionBar(binding.toolBar)
    requireActivityAsAppCompatActivity().supportActionBar?.title = ""
    supportActionBar()?.setDisplayHomeAsUpEnabled(true)

    binding.btnRetry.setOnClickListener {
      viewModel.loadData()
    }

    viewModel.viewItemLiveData.observe(this, Observer(::observeViewItem))
    viewModel.showContactDialogEvent.observe(this, Observer(::showContactDialog))

    if (savedViewState == null) {
      viewModel.loadData()
    }
  }

  private val progresBarVisibilityHandler by lazy {
    ViewVisibilityDebounceHandler(binding.progressBar)
  }

  private fun observeViewItem(viewState: AsyncViewState<PartyDetailViewItem>) {
    progresBarVisibilityHandler.setVisible(viewState is AsyncViewState.Loading)
    binding.layoutContent.isVisible = viewState is AsyncViewState.Success
    binding.tvErrorMessage.isVisible = viewState is AsyncViewState.Error
    binding.btnRetry.isVisible = viewState is AsyncViewState.Error

    when (viewState) {
      is AsyncViewState.Success -> {
        val viewItem = viewState.value
        binding.ivPartySeal.load(viewItem.sealImage) {
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
          startActivity(Intents.viewUrl(viewItem.policy))
        }

        binding.ivPartyFlag.load(viewItem.flagImage) {
          placeholder(R.drawable.placeholder_rect)
          error(R.drawable.placeholder_rect)
          crossfade(true)
        }

        binding.tvPartyNumber.text = viewItem.partyNumber.convertToBurmeseNumber()

        binding.tvPartyRegion.text = viewItem.region
        binding.tvLeader.text = viewItem.leadersAndChairmen
        binding.tvMemberCount.text = viewItem.memberCount
        binding.tvHeadquarterLocation.text = viewItem.headQuarterLocation

        binding.tvContactTitle.isVisible = viewItem.contactList.isNotEmpty()
        binding.tvContact.isVisible = viewItem.contactList.isNotEmpty()
        binding.tvContact.text = viewItem.contactList.format("၊")
        binding.tvContact.setOnClickListener {
          viewModel.handleContactClick(viewItem.contactList)
        }

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

  private fun showContactDialog(contactViewItemList: List<PartyContactViewItem>) {
    MaterialAlertDialogBuilder(requireActivity())
      .setItems(contactViewItemList.map { it.text }.toTypedArray()) { dialog, which ->
        contactViewItemList.getOrNull(which)?.let { itemAtIndex ->
          startActivity(Intents.dialIntent(itemAtIndex.number))
        }
      }
      .setNegativeButton(R.string.cancel) { dialog, _ ->
        dialog?.cancel()
      }
      .create()
      .also { dialog ->
        dialog.setOnShowListener {
          val negativeButton = dialog.getButton(DialogInterface.BUTTON_NEGATIVE)
          negativeButton.setTextColor(ContextCompat.getColor(requireContext(), R.color.text_error))
        }
      }
      .show()
  }
}