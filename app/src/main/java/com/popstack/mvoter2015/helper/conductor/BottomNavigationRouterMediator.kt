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
  private var lastSelectedId: Int? = null

  private var currentPrimaryRouter: Router? = null

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

      if (menuItem.itemId != lastSelectedId) {

        val position = bottomNavigationView.menu.indexOf(menuItem)
        destroyPrevious(position)

        host.getChildRouter(container, getRouterName(menuItem))
          .apply {
            if (!hasRootController()) {
              val savedState = savedPages.get(position)
              if (savedState != null) {
                restoreInstanceState(savedState)
                savedPages.remove(position)
              }
              rebindIfNeeded()
              val transaction = menuIdAndTransactionMap.getValue(menuItem.itemId)
              setRoot(transaction.invoke())

              if (this != currentPrimaryRouter) {
                currentPrimaryRouter = this
              }

            }

          }

        lastSelectedId = menuItem.itemId
        return@OnNavigationItemSelectedListener true
      }

      return@OnNavigationItemSelectedListener false
    }

  private fun destroyPrevious(position: Int) {
    currentPrimaryRouter?.let { router ->
      val savedStated = Bundle()
      router.saveInstanceState(savedStated)
      savedPages.put(position, savedStated)

      host.removeChildRouter(router)
      currentPrimaryRouter = null
    }
  }

  fun setup() {
    bottomNavigationView.setOnNavigationItemSelectedListener(navigationItemSelectedListener)
  }

  private fun getRouterName(menuItem: MenuItem): String {
    return menuItem.itemId.toString()
  }

}