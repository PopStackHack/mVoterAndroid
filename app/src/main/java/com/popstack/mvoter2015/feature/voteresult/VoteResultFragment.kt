package com.popstack.mvoter2015.feature.voteresult

import android.view.LayoutInflater
import androidx.viewbinding.ViewBinding
import com.popstack.mvoter2015.core.mvp.MvpFragment
import com.popstack.mvoter2015.databinding.FragmentInfoBinding
import com.popstack.mvoter2015.databinding.FragmentVoteResultBinding
import javax.inject.Inject

class VoteResultFragment : MvpFragment<FragmentVoteResultBinding, VoteResultView, VoteResultViewModel>(),
    VoteResultView {

  override val viewModel: VoteResultViewModel by contractedViewModels()

  override val bindingInflater: (LayoutInflater) -> ViewBinding =
    FragmentVoteResultBinding::inflate

}