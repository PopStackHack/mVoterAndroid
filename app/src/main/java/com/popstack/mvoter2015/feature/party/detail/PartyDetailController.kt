package com.popstack.mvoter2015.feature.party.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.os.bundleOf
import com.popstack.mvoter2015.core.mvp.MvpController
import com.popstack.mvoter2015.databinding.ControllerPartyDetailBinding
import com.popstack.mvoter2015.domain.party.model.PartyId

class PartyDetailController(
  private val bundle: Bundle
) : MvpController<ControllerPartyDetailBinding, PartyDetailView, PartyDetailViewModel>(bundle) {

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

  override val viewModel: PartyDetailViewModel by contractedViewModels()

  override val bindingInflater: (LayoutInflater, ViewGroup) -> ControllerPartyDetailBinding
    get() = { layoutInflater, viewGroup ->
      ControllerPartyDetailBinding.inflate(layoutInflater, viewGroup, false)
    }

  override fun onBindView() {
    super.onBindView()
  }
}