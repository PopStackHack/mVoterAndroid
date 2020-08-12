package com.popstack.mvoter2015.feature.candidate.listing

import com.bluelinelabs.conductor.Controller
import com.bluelinelabs.conductor.Router
import com.bluelinelabs.conductor.RouterTransaction
import com.bluelinelabs.conductor.viewpager.RouterPagerAdapter
import com.popstack.mvoter2015.domain.constituency.model.HouseType
import com.popstack.mvoter2015.feature.candidate.listing.lowerhouse.LowerHouseCandidateListController
import com.popstack.mvoter2015.feature.candidate.listing.regionalhouse.RegionalHouseCandidateListController
import com.popstack.mvoter2015.feature.candidate.listing.upperhouse.UpperHouseCandidateListController
import com.popstack.mvoter2015.sentry.BreadcrumbControllerChangeHandler

internal class CandidateListHousePagerAdapter(host: Controller) :
  RouterPagerAdapter(host) {

  private var itemList = listOf<CandidateListHouseViewItem>()

  fun setItems(itemList: List<CandidateListHouseViewItem>) {
    this.itemList = itemList
    notifyDataSetChanged()
  }

  override fun getPageTitle(position: Int): CharSequence? {
    return itemList[position].name
  }

  override fun configureRouter(
    router: Router,
    position: Int
  ) {
    router.addChangeListener(BreadcrumbControllerChangeHandler)
    if (!router.hasRootController()) {
      val routerTransaction = when (itemList[position].houseType) {
        HouseType.UPPER_HOUSE -> {
          RouterTransaction.with(UpperHouseCandidateListController())
        }
        HouseType.LOWER_HOUSE -> {
          RouterTransaction.with(LowerHouseCandidateListController())
        }
        HouseType.REGIONAL_HOUSE -> {
          RouterTransaction.with(RegionalHouseCandidateListController())
        }
      }
      router.setRoot(routerTransaction)
    }
  }

  override fun getCount(): Int {
    return itemList.size
  }

}