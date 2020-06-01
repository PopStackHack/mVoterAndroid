package com.popstack.mvoter2015.helper.conductor

import android.view.ViewGroup
import androidx.core.view.forEach
import com.bluelinelabs.conductor.Controller
import com.bluelinelabs.conductor.Router
import com.bluelinelabs.conductor.RouterTransaction
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.popstack.mvoter2015.helper.extensions.filter

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
  val menuIdAndTransactionMap: Map<Int, RouterTransaction>
) {

  private val itemIdAndChildRouterMap: Map<Int, Router>

  init {
    require(
      bottomNavigationView.menu.filter {
        menuIdAndTransactionMap.containsKey(
          it.itemId
        )
      }) { "All menu items must have a matching page setup!" }

    val tempMap = mutableMapOf<Int, Router>()
    bottomNavigationView.menu.forEach { menuItem ->
      tempMap[menuItem.itemId] = host.getChildRouter(container, menuItem.itemId.toString())
    }

    itemIdAndChildRouterMap = tempMap
  }

  private val navigationItemSelectedListener =
    BottomNavigationView.OnNavigationItemSelectedListener { item ->

      val transaction = menuIdAndTransactionMap.getValue(item.itemId)

      itemIdAndChildRouterMap.getValue(item.itemId)
        .setRoot(transaction)

      return@OnNavigationItemSelectedListener true
    }

  fun setup() {
    bottomNavigationView.setOnNavigationItemSelectedListener(navigationItemSelectedListener)
  }

  val childRouters
    get() = itemIdAndChildRouterMap.values

}