package com.popstack.mvoter2015.feature.howtovote

import android.view.LayoutInflater
import androidx.viewbinding.ViewBinding
import com.popstack.mvoter2015.core.mvp.MvpFragment
import com.popstack.mvoter2015.databinding.FragmentHowToVoteBinding
import com.popstack.mvoter2015.databinding.FragmentPartyListBinding
import javax.inject.Inject

class HowToVoteFragment : MvpFragment<FragmentHowToVoteBinding, HowToVoteView, HowToVoteViewModel>(),
    HowToVoteView {

  override val viewModel: HowToVoteViewModel by contractedViewModels()

  override val bindingInflater: (LayoutInflater) -> ViewBinding =
    FragmentHowToVoteBinding::inflate

}