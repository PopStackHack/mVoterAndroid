package com.popstack.mvoter2015.helper.conductor

/**
 * Copied from https://gist.github.com/fonix232/14294caea86c4161478f5263a41fc50b
 */
import android.os.Bundle
import android.os.Parcelable
import android.util.SparseArray
import android.view.View
import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.core.view.get
import androidx.viewpager.widget.PagerAdapter
import com.bluelinelabs.conductor.Controller
import com.bluelinelabs.conductor.Router
import com.bluelinelabs.conductor.RouterTransaction
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.popstack.mvoter2015.helper.viewpager.NonScrollableViewPager
import com.popstack.mvoter2015.helper.extensions.filter
import com.popstack.mvoter2015.helper.extensions.indexOf
import com.popstack.mvoter2015.logging.BreadcrumbControllerChangeHandler

class BNVRouterPagerAdapter(
  val host: Controller,
  val bnv: BottomNavigationView,
  val viewPager: NonScrollableViewPager,
  val pages: Map<Int, () -> Controller>
) : PagerAdapter() {

  companion object {
    private const val KEY_SAVED_PAGES = "BottomBarRouterPagerAdapter.savedStates"
    private const val KEY_MAX_PAGES_TO_STATE_SAVE =
      "BottomBarRouterPagerAdapter.maxPagesToStateSave"
    private const val KEY_SAVE_PAGE_HISTORY = "BottomBarRouterPagerAdapter.savedPageHistory"
  }

  private var maxPagesToStateSave = Integer.MAX_VALUE
  private val visibleRouters = SparseArray<Router>()

  var currentPrimaryRouter: Router? = null
    private set

  var savedPageHistory = ArrayList<Int>()
    private set

  var savedPages: SparseArray<Bundle> = SparseArray()
    private set

  init {
    require(bnv.menu.filter { pages.containsKey(it.itemId) }) { "All menu items must have a matching page setup!" }
    viewPager.adapter = this
    bnv.setOnNavigationItemSelectedListener {
      viewPager.currentItem = bnv.menu.indexOf(it)
      true
    }
  }

  /**
   * Called when a router is instantiated. Here the router's root should be set if needed.
   *
   * @param router The router used for the page
   * @param position The page position to be instantiated.
   */
  fun configureRouter(@NonNull router: Router, position: Int) {
    if (!router.hasRootController()) {
      val page =
        pages[bnv.menu[position].itemId] ?: throw Exception("Page not found in initializers!")
      router.setRoot(RouterTransaction.with(page.invoke()))
    }

  }

  override fun getCount(): Int = bnv.menu.size()
  override fun getPageTitle(position: Int): CharSequence? = bnv.menu[position].title

  override fun instantiateItem(container: ViewGroup, position: Int): Any {
    return host.getChildRouter(container, makeRouterName(container.id, getItemId(position))).apply {
      this.addChangeListener(BreadcrumbControllerChangeHandler)
      if (!hasRootController()) {
        val savedState = savedPages.get(position)
        if (savedState != null) {
          restoreInstanceState(savedState)
          savedPages.remove(position)
          savedPageHistory.remove(position)
        }
        rebindIfNeeded()
        configureRouter(this, position)
        if (this !== currentPrimaryRouter) {
          backstack.forEach { it.controller.setOptionsMenuHidden(true) }
        }
        visibleRouters.put(position, this)
      }
    }
  }

  override fun destroyItem(container: ViewGroup, position: Int, router: Any) {
    require(router is Router) { "Non-router object in router stack!" }

    val savedState = Bundle()
    router.saveInstanceState(savedState)
    savedPages.put(position, savedState)

    savedPageHistory.remove(position)
    savedPageHistory.add(position)

    ensurePagesSaved()

    router.removeChangeListener(BreadcrumbControllerChangeHandler)
    host.removeChildRouter(router)
    visibleRouters.remove(position)
  }

  override fun setPrimaryItem(container: ViewGroup, position: Int, router: Any) {
    require(router is Router) { "Non-router object in router stack!" }

    if (router !== currentPrimaryRouter) {
      currentPrimaryRouter?.backstack?.forEach { it.controller.setOptionsMenuHidden(true) }
      router.backstack.forEach { it.controller.setOptionsMenuHidden(false) }
      currentPrimaryRouter = router
    }
  }

  override fun isViewFromObject(view: View, router: Any): Boolean {
    require(router is Router) { "Non-router object in router stack!" }

    for (transaction in router.backstack) if (transaction.controller.view == view) return true
    return false
  }

  fun setMaxPagesToStateSave(maxPagesToStateSave: Int) {
    require(maxPagesToStateSave >= 0) { "Only positive integers may be passed for maxPagesToStateSave." }

    this.maxPagesToStateSave = maxPagesToStateSave

    ensurePagesSaved()
  }

  override fun saveState(): Parcelable? {
    return (super.saveState() as? Bundle? ?: Bundle()).apply {
      putSparseParcelableArray(KEY_SAVED_PAGES, savedPages)
      putInt(KEY_MAX_PAGES_TO_STATE_SAVE, maxPagesToStateSave)
      putIntegerArrayList(KEY_SAVE_PAGE_HISTORY, savedPageHistory)
    }
  }

  override fun restoreState(state: Parcelable?, loader: ClassLoader?) {
    super.restoreState(state, loader)
    (state as? Bundle?)?.apply {
      savedPages = getSparseParcelableArray(KEY_SAVED_PAGES) ?: SparseArray()
      maxPagesToStateSave = getInt(KEY_MAX_PAGES_TO_STATE_SAVE)
      savedPageHistory = getIntegerArrayList(KEY_SAVE_PAGE_HISTORY) as ArrayList<Int>
    }
  }

  fun getRouter(position: Int): Router = visibleRouters[position, null]

  fun getItemId(position: Int): Long {
    return bnv.menu.getItem(position).itemId.toLong()
  }

  private fun ensurePagesSaved() {
    while (savedPages.size() > maxPagesToStateSave) {
      val positionToRemove = savedPageHistory.removeAt(0)
      savedPages.remove(positionToRemove)
    }
  }

  private fun makeRouterName(viewId: Int, id: Long): String {
    return "$viewId:$id"
  }
}