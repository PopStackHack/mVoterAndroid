package com.popstack.mvoter2015.feature.candidate.listing

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.popstack.mvoter2015.domain.house.HouseType
import com.popstack.mvoter2015.feature.candidate.listing.lowerhouse.LowerHouseCandidateListFragment
import com.popstack.mvoter2015.feature.candidate.listing.regionalhouse.RegionalHouseCandidateListFragment
import com.popstack.mvoter2015.feature.candidate.listing.upperhouse.UpperHouseCandidateListFragment

internal class CandidateListHousePagerAdapter(
  fragmentManager: FragmentManager,
  lifecycle: Lifecycle
) :
  FragmentStateAdapter(fragmentManager, lifecycle) {

  private var itemList = listOf<CandidateListHouseViewItem>()

  fun setItems(itemList: List<CandidateListHouseViewItem>) {
    this.itemList = itemList
    notifyDataSetChanged()
  }

  fun getTitleAtPosition(position: Int): String {
    return itemList[position].name
  }

  override fun getItemCount(): Int {
    return itemList.size
  }

  override fun createFragment(position: Int): Fragment {
    return when (itemList[position].houseType) {
      HouseType.UPPER_HOUSE -> {
        UpperHouseCandidateListFragment()
      }
      HouseType.LOWER_HOUSE -> {
        LowerHouseCandidateListFragment()
      }

      HouseType.REGIONAL_HOUSE -> {
        RegionalHouseCandidateListFragment()
      }
    }
  }

}



