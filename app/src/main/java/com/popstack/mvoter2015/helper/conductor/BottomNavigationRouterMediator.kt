package com.popstack.mvoter2015.helper.conductor

import android.os.Bundle
import android.util.SparseArray
import android.view.MenuItem
import android.view.ViewGroup
import com.bluelinelabs.conductor.Controller
import com.bluelinelabs.conductor.Router
import com.bluelinelabs.conductor.RouterTransaction
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.popstack.mvoter2015.helper.extensions.filter
import com.popstack.mvoter2015.helper.extensions.indexOf

/**
 * Modified from https://gist.github.com/fonix232/14294caea86c4161478f5263a41fc50b
 * PR : https://github.com/bluelinelabs/Conductor/pull/565
 *
 * A Mediator that handles bottom nav and router's controller in their own stack
 *
 * @param host: Host Controller
 * @param container Container View that the router will push controller to
 * @param bottomNavigationView Bottom Navigation View to link with
 * @param menuIdAndTransactionMap A map that MUST contains transaction linked to each menu id defined for [bottomNavigationView]'s menu resource
 */
class BottomNavigationRouterMediator(
  val host: Controller,
  val container: ViewGroup,
  val bottomNavigationView: BottomNavigationView,
  val menuIdAndTransactionMap: Map<Int, () -> RouterTransaction>
) {

  private val savedPages: SparseArray<Bundle> = SparseArray()
//  private var lastSelectedId: Int? = null

  private var currentPrimaryRouter: Router? = null
  private var lastSelectedIndex = -1

  init {
    require(
      bottomNavigationView.menu.filter {
        menuIdAndTransactionMap.containsKey(
          it.itemId
        )
      }) { "All menu items must have a matching page setup!" }
  }

  private val navigationItemSelectedListener =
    BottomNavigationView.OnNavigationItemSelectedListener { menuItem ->

      val position = bottomNavigationView.menu.indexOf(menuItem)
      val router = host.getChildRouter(container, getRouterName(menuItem, position))

      if (router != currentPrimaryRouter) {

        //First destroy the previous router instance
        destroyPrevious()

        if (!router.hasRootController()) {
          //Check if there's already an saved state
          val savedState = savedPages.get(position)
          if (savedState != null) {
            //Previous state exists, restore
            router.restoreInstanceState(savedState)
            savedPages.remove(position)
          } else {
            //Previous state does not exist, we create a new one
            val transaction = menuIdAndTransactionMap.getValue(menuItem.itemId)
            router.setRoot(transaction.invoke())
          }
          router.rebindIfNeeded()
        }

        lastSelectedIndex = position
        currentPrimaryRouter = router

        return@OnNavigationItemSelectedListener true
      }

      return@OnNavigationItemSelectedListener false
    }

  private fun destroyPrevious() {
    currentPrimaryRouter?.let { router ->
      val savedStated = Bundle()
      router.saveInstanceState(savedStated)
      savedPages.put(lastSelectedIndex, savedStated)

      host.removeChildRouter(router)
      currentPrimaryRouter = null
    }
  }

  fun attach() {
    bottomNavigationView.setOnNavigationItemSelectedListener(navigationItemSelectedListener)
  }

  private fun getRouterName(
    menuItem: MenuItem,
    position: Int
  ): String {
    return "${menuItem.itemId}_$position"
  }

}