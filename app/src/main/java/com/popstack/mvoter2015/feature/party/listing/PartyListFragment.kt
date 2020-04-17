package com.popstack.mvoter2015.feature.party.listing

import android.view.LayoutInflater
import androidx.viewbinding.ViewBinding
import com.popstack.mvoter2015.core.mvp.MvpFragment
import com.popstack.mvoter2015.databinding.FragmentPartyListBinding

class PartyListFragment : MvpFragment<FragmentPartyListBinding, PartyListView, PartyListViewModel>(),
    PartyListView {

  override val viewModel: PartyListViewModel by contractedViewModels()

  override val bindingInflater: (LayoutInflater) -> ViewBinding =
    FragmentPartyListBinding::inflate

}