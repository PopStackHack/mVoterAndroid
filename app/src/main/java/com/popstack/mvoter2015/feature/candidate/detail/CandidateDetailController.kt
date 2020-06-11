package com.popstack.mvoter2015.feature.candidate.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import com.popstack.mvoter2015.core.mvp.MvvmController
import com.popstack.mvoter2015.databinding.ControllerCandidateDetailBinding
import com.popstack.mvoter2015.domain.candidate.model.CandidateId

class CandidateDetailController(
  bundle: Bundle? = null
) : MvvmController<ControllerCandidateDetailBinding>(
  bundle
) {

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

//  override fun getCandidateId(): CandidateId = CandidateId(args.getString(ARG_CANDIDATE_ID)!!)

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup,
    savedViewState: Bundle?
  ): View {
    return super.onCreateView(inflater, container, savedViewState)
  }

  override fun onBindView() {
    super.onBindView()
    viewModel.loadData()
  }

}