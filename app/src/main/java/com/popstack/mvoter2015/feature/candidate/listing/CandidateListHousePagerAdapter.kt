package com.popstack.mvoter2015.feature.candidate.listing

import com.bluelinelabs.conductor.Controller
import com.bluelinelabs.conductor.Router
import com.bluelinelabs.conductor.RouterTransaction
import com.bluelinelabs.conductor.viewpager.RouterPagerAdapter
import com.popstack.mvoter2015.feature.candidate.listing.lowerhouse.LowerHouseCandidateListController
import com.popstack.mvoter2015.feature.candidate.listing.regionalhouse.RegionalHouseCandidateListController
import com.popstack.mvoter2015.feature.candidate.listing.upperhouse.UpperHouseCandidateListController
import com.popstack.mvoter2015.logging.BreadcrumbControllerChangeHandler

internal class CandidateListHousePagerAdapter(host: Controller) :
  RouterPagerAdapter(host) {

  private var itemList = listOf<CandidateListHouseViewItem>()

  fun setItems(itemList: List<CandidateListHouseViewItem>) {
    this.itemList = itemList
    notifyDataSetChanged()
  }

  override fun getPageTitle(position: Int): CharSequence? {
    return itemList[position].houseName
  }

  override fun configureRouter(
    router: Router,
    position: Int
  ) {
    router.addChangeListener(BreadcrumbControllerChangeHandler)
    if (!router.hasRootController()) {
      val controller = when (position) {
        0 -> LowerHouseCandidateListController()
        1 -> UpperHouseCandidateListController()
        2 -> RegionalHouseCandidateListController()
        else -> throw IllegalStateException()
      }
      val routerTransaction = RouterTransaction.with(controller)
      router.setRoot(routerTransaction)
    }
  }

  override fun getCount(): Int {
    return itemList.size
  }

}