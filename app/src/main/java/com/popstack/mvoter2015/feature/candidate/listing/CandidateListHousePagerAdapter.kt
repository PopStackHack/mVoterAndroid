package com.popstack.mvoter2015.feature.candidate.listing

import com.bluelinelabs.conductor.Controller
import com.bluelinelabs.conductor.Router
import com.bluelinelabs.conductor.RouterTransaction
import com.bluelinelabs.conductor.viewpager.RouterPagerAdapter
import com.popstack.mvoter2015.di.conductor.ControllerInjectorChangeHandler
import com.popstack.mvoter2015.domain.constituency.model.ConstituencyId
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
    return itemList[position].name
  }

  override fun configureRouter(
    router: Router,
    position: Int
  ) {
    router.addChangeListener(BreadcrumbControllerChangeHandler)
    router.addChangeListener(ControllerInjectorChangeHandler)
    if (!router.hasRootController()) {
      val controller = when (position) {
        0 -> UpperHouseCandidateListController.newInstance(ConstituencyId(itemList[position].constituencyId), itemList[position].houseType)
        1 -> LowerHouseCandidateListController.newInstance(ConstituencyId(itemList[position].constituencyId), itemList[position].houseType)
        2 -> RegionalHouseCandidateListController.newInstance(ConstituencyId(itemList[position].constituencyId), itemList[position].houseType)
        else -> return
      }
      val routerTransaction = RouterTransaction.with(controller)
      router.setRoot(routerTransaction)
    }
  }

  override fun getCount(): Int {
    return itemList.size
  }

}