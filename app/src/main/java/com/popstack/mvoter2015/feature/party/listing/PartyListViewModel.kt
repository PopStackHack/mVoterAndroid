package com.popstack.mvoter2015.feature.party.listing

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class PartyListViewModel @ViewModelInject constructor() : ViewModel() {

  private val viewItemLiveData = MutableLiveData<List<PartyListViewItem>>()

}