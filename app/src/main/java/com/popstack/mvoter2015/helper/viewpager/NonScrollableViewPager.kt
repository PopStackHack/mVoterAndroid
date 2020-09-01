package com.popstack.mvoter2015.helper.viewpager

import android.content.Context
import android.database.DataSetObserver
import android.graphics.Rect
import android.os.Parcel
import android.os.Parcelable
import android.os.Parcelable.ClassLoaderCreator
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.ViewParent
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.customview.view.AbsSavedState
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager.OnPageChangeListener
import java.util.ArrayList

class NonScrollableViewPager : ViewGroup {
  var mAdapter: PagerAdapter? = null
  private var mCurrentItem: ItemInfo? = null
  var mCurItem = 0
  private var mRestoredCurItem = -1
  private var mRestoredAdapterState: Parcelable? = null
  private var mRestoredClassLoader: ClassLoader? = null
  private val mObserver: DataSetObserver = object : DataSetObserver() {
    override fun onChanged() {
      dataSetChanged()
    }

    override fun onInvalidated() {
      dataSetChanged()
    }
  }
  private var mInLayout = false
  private var mFirstLayout = true

  class ItemInfo {
    var `object`: Any? = null
    var position = 0
  }

  private var mOnPageChangeListeners: MutableList<OnPageChangeListener>? = null

  constructor(context: Context) : super(context) {
    initViewPager()
  }

  constructor(context: Context, attrs: AttributeSet?) : super(
    context,
    attrs
  ) {
    initViewPager()
  }

  fun initViewPager() {
    setWillNotDraw(false)
    descendantFocusability = FOCUS_AFTER_DESCENDANTS
    isFocusable = true
    ViewCompat.setOnApplyWindowInsetsListener(
      this,
      object : androidx.core.view.OnApplyWindowInsetsListener {
        private val mTempRect = Rect()
        override fun onApplyWindowInsets(
          v: View,
          originalInsets: WindowInsetsCompat
        ): WindowInsetsCompat {
          // First let the ViewPager itself try and consume them...
          val applied =
            ViewCompat.onApplyWindowInsets(v, originalInsets)
          if (applied.isConsumed) {
            // If the ViewPager consumed all insets, return now
            return applied
          }

          // Now we'll manually dispatch the insets to our children. Since ViewPager
          // children are always full-height, we do not want to use the standard
          // ViewGroup dispatchApplyWindowInsets since if child 0 consumes them,
          // the rest of the children will not receive any insets. To workaround this
          // we manually dispatch the applied insets, not allowing children to
          // consume them from each other. We do however keep track of any insets
          // which are consumed, returning the union of our children's consumption
          val res = mTempRect
          res.left = applied.systemWindowInsetLeft
          res.top = applied.systemWindowInsetTop
          res.right = applied.systemWindowInsetRight
          res.bottom = applied.systemWindowInsetBottom
          var i = 0
          val count = childCount
          while (i < count) {
            val childInsets = ViewCompat
              .dispatchApplyWindowInsets(getChildAt(i), applied)
            // Now keep track of any consumed by tracking each dimension's min
            // value
            res.left = Math.min(
              childInsets.systemWindowInsetLeft,
              res.left
            )
            res.top = Math.min(
              childInsets.systemWindowInsetTop,
              res.top
            )
            res.right = Math.min(
              childInsets.systemWindowInsetRight,
              res.right
            )
            res.bottom = Math.min(
              childInsets.systemWindowInsetBottom,
              res.bottom
            )
            i++
          }

          // Now return a new WindowInsets, using the consumed window insets
          return applied.replaceSystemWindowInsets(
            res.left, res.top, res.right, res.bottom
          )!!
        }
      }
    )
  }

  /**
   * Add a listener that will be invoked whenever the page changes or is incrementally
   * scrolled. See [ViewPager.OnPageChangeListener].
   *
   *
   * Components that add a listener should take care to remove it when finished.
   * Other components that take ownership of a view may call [.clearOnPageChangeListeners]
   * to remove all attached listeners.
   *
   * @param listener listener to add
   */
  fun addOnPageChangeListener(listener: OnPageChangeListener) {
    if (mOnPageChangeListeners == null) {
      mOnPageChangeListeners = ArrayList()
    }
    mOnPageChangeListeners!!.add(listener)
  }

  /**
   * Remove a listener that was previously added via
   * [.addOnPageChangeListener].
   *
   * @param listener listener to remove
   */
  fun removeOnPageChangeListener(listener: OnPageChangeListener) {
    if (mOnPageChangeListeners != null) {
      mOnPageChangeListeners!!.remove(listener)
    }
  }

  /**
   * Remove all listeners that are notified of any changes in scroll state or position.
   */
  fun clearOnPageChangeListeners() {
    if (mOnPageChangeListeners != null) {
      mOnPageChangeListeners!!.clear()
    }
  }

  private fun dispatchOnPageSelected(position: Int) {
    if (mOnPageChangeListeners != null) {
      var i = 0
      val z = mOnPageChangeListeners!!.size
      while (i < z) {
        val listener = mOnPageChangeListeners!![i]
        listener?.onPageSelected(position)
        i++
      }
    }
  }

  /**
   * Retrieve the current adapter supplying pages.
   *
   * @return The currently registered PagerAdapter
   */
  /**
   * Set a PagerAdapter that will supply views for this pager as needed.
   *
   * @param adapter Adapter to use
   */
  var adapter: PagerAdapter?
    get() = mAdapter
    set(adapter) {
      if (mAdapter != null) {
        mAdapter!!.unregisterDataSetObserver(mObserver)
        mAdapter!!.startUpdate(this)
        if (mCurrentItem != null) {
          mAdapter!!.destroyItem(this, mCurrentItem!!.position, mCurrentItem!!.`object`!!)
          mCurrentItem = null
        }
        mAdapter!!.finishUpdate(this)
        removeAllViews()
        mCurItem = 0
      }
      mAdapter = adapter
      if (mAdapter != null) {
        mAdapter!!.registerDataSetObserver(mObserver)
        val wasFirstLayout = mFirstLayout
        mFirstLayout = true
        if (mRestoredCurItem >= 0) {
          mAdapter!!.restoreState(mRestoredAdapterState, mRestoredClassLoader)
          setCurrentItemInternal(mRestoredCurItem, true)
          mRestoredCurItem = -1
          mRestoredAdapterState = null
          mRestoredClassLoader = null
        } else if (!wasFirstLayout) {
          populate()
        } else {
          requestLayout()
        }
      }
    }

  /**
   * Set the currently selected page.
   *
   * @param item Item index to select
   */
  var currentItem: Int
    get() = mCurItem
    set(item) {
      setCurrentItemInternal(item, false)
    }

  fun setCurrentItemInternal(item: Int, always: Boolean) {
    var item = item
    if (mAdapter == null || mAdapter!!.count <= 0) {
      return
    }
    if (!always && mCurItem == item && mCurrentItem != null) {
      return
    }
    if (item < 0) {
      item = 0
    } else if (item >= mAdapter!!.count) {
      item = mAdapter!!.count - 1
    }
    val dispatchSelected = mCurItem != item
    if (mFirstLayout) {
      // We don't have any idea how big we are yet and shouldn't have any pages either.
      // Just set things up and let the pending layout handle things.
      mCurItem = item
      if (dispatchSelected) {
        dispatchOnPageSelected(item)
      }
      requestLayout()
    } else {
      populate(item)
      if (dispatchSelected) {
        dispatchOnPageSelected(item)
      }
    }
  }

  fun dataSetChanged() {
    val adapterCount = mAdapter!!.count
    var needPopulate = false
    var newCurrItem = mCurItem
    if (mCurrentItem != null) {
      val newPos = mAdapter!!.getItemPosition(mCurrentItem!!.`object`!!)
      if (newPos == PagerAdapter.POSITION_NONE) {
        needPopulate = true
        newCurrItem = Math.max(0, Math.min(mCurItem, adapterCount - 1))
      }
    } else {
      needPopulate = true
    }
    if (needPopulate) {
      // Reset our known page widths; populate will recompute them.
      val childCount = childCount
      for (i in 0 until childCount) {
        val child = getChildAt(i)
        val lp =
          child.layoutParams as LayoutParams
        lp.needsPositionUpdate = true
      }
      setCurrentItemInternal(newCurrItem, true)
      requestLayout()
    }
  }

  @JvmOverloads
  fun populate(newCurrentItem: Int = mCurItem) {
    mCurItem = newCurrentItem
    if (mAdapter == null) {
      return
    }
    if (windowToken == null) {
      return
    }
    mAdapter!!.startUpdate(this)
    if (mCurrentItem != null) {
      if (mCurrentItem!!.position != newCurrentItem) {
        mAdapter!!.destroyItem(this, mCurrentItem!!.position, mCurrentItem!!.`object`!!)
        mCurrentItem = null
      }
    }
    if (mCurrentItem == null) {
      mCurrentItem = addNewItem(newCurrentItem)
    }
    mAdapter!!.setPrimaryItem(this, mCurItem, mCurrentItem!!.`object`!!)
    mAdapter!!.finishUpdate(this)

    // Check width measurement of current pages and drawing sort order.
    // Update LayoutParams as needed.
    val childCount = childCount
    for (i in 0 until childCount) {
      val child = getChildAt(i)
      val lp =
        child.layoutParams as LayoutParams
      lp.childIndex = i
      if (lp.needsPositionUpdate) {
        val ii = infoForChild(child)
        if (ii != null) {
          lp.position = ii.position
        }
      }
    }
    if (hasFocus()) {
      val currentFocused = findFocus()
      var ii = currentFocused?.let { infoForAnyChild(it) }
      if (ii == null || ii.position != mCurItem) {
        for (i in 0 until getChildCount()) {
          val child = getChildAt(i)
          ii = infoForChild(child)
          if (ii != null && ii.position == mCurItem) {
            if (child.requestFocus(View.FOCUS_FORWARD)) {
              break
            }
          }
        }
      }
    }
  }

  override fun addView(
    child: View,
    index: Int,
    params: ViewGroup.LayoutParams
  ) {
    var params = params
    if (!checkLayoutParams(params)) {
      params = generateLayoutParams(params)
    }
    val lp = params as LayoutParams
    if (mInLayout) {
      lp.needsMeasure = true
      addViewInLayout(child, index, params)
    } else {
      super.addView(child, index, params)
    }
  }

  override fun removeView(view: View) {
    if (mInLayout) {
      removeViewInLayout(view)
    } else {
      super.removeView(view)
    }
  }

  override fun generateDefaultLayoutParams(): ViewGroup.LayoutParams {
    return LayoutParams()
  }

  override fun generateLayoutParams(p: ViewGroup.LayoutParams): ViewGroup.LayoutParams {
    return generateDefaultLayoutParams()
  }

  override fun checkLayoutParams(p: ViewGroup.LayoutParams): Boolean {
    return p is LayoutParams && super.checkLayoutParams(p)
  }

  override fun generateLayoutParams(attrs: AttributeSet): ViewGroup.LayoutParams {
    return LayoutParams(
      context,
      attrs
    )
  }

  fun infoForChild(child: View?): ItemInfo? {
    if (mCurrentItem != null) {
      if (mAdapter!!.isViewFromObject(child!!, mCurrentItem!!.`object`!!)) {
        return mCurrentItem
      }
    }
    return null
  }

  fun infoForAnyChild(child: View): ItemInfo? {
    var child = child
    var parent: ViewParent?
    while (child.parent.also { parent = it } !== this) {
      if (parent !is View) {
        return null
      }
      child = parent as View
    }
    return infoForChild(child)
  }

  fun addNewItem(position: Int): ItemInfo {
    val ii =
      ItemInfo()
    ii.position = position
    ii.`object` = mAdapter!!.instantiateItem(this, position)
    return ii
  }

  override fun onTouchEvent(event: MotionEvent): Boolean {
    return false
  }

  override fun onInterceptTouchEvent(ev: MotionEvent): Boolean {
    return false
  }

  override fun onAttachedToWindow() {
    super.onAttachedToWindow()
    mFirstLayout = true
  }

  override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
    // For simple implementation, our internal size is always 0.
    // We depend on the container to specify the layout size of
    // our view.  We can't really know what it is since we will be
    // adding and removing different arbitrary views and do not
    // want the layout to change as this happens.
    setMeasuredDimension(
      View.getDefaultSize(0, widthMeasureSpec),
      View.getDefaultSize(0, heightMeasureSpec)
    )
    val measuredWidth = measuredWidth

    // Children are just made to fill our space.
    val childWidthSize = measuredWidth - paddingLeft - paddingRight
    val childHeightSize = measuredHeight - paddingTop - paddingBottom
    val mChildWidthMeasureSpec =
      MeasureSpec.makeMeasureSpec(childWidthSize, MeasureSpec.EXACTLY)
    val mChildHeightMeasureSpec =
      MeasureSpec.makeMeasureSpec(childHeightSize, MeasureSpec.EXACTLY)

    // Make sure we have created all fragments that we need to have shown.
    mInLayout = true
    populate()
    mInLayout = false

    // Page views next.
    val size = childCount
    for (i in 0 until size) {
      val child = getChildAt(i)
      if (child.visibility != View.GONE) {
        child.measure(mChildWidthMeasureSpec, mChildHeightMeasureSpec)
      }
    }
  }

  override fun onLayout(
    changed: Boolean,
    l: Int,
    t: Int,
    r: Int,
    b: Int
  ) {
    val count = childCount
    val width = r - l
    val height = b - t
    val paddingLeft = paddingLeft
    val paddingTop = paddingTop
    val paddingRight = paddingRight
    val paddingBottom = paddingBottom
    val childWidth = width - paddingLeft - paddingRight
    // Page views. Do this once we have the right padding offsets from above.
    for (i in 0 until count) {
      val child = getChildAt(i)
      if (child.visibility != View.GONE) {
        val lp =
          child.layoutParams as LayoutParams
        if (infoForChild(child) != null) {
          if (lp.needsMeasure) {
            // This was added during layout and needs measurement.
            // Do it now that we know what we're working with.
            lp.needsMeasure = false
            val widthSpec = MeasureSpec.makeMeasureSpec(
              childWidth,
              MeasureSpec.EXACTLY
            )
            val heightSpec = MeasureSpec.makeMeasureSpec(
              height - paddingTop - paddingBottom,
              MeasureSpec.EXACTLY
            )
            child.measure(widthSpec, heightSpec)
          }
          child.layout(
            paddingLeft, paddingTop,
            paddingLeft + child.measuredWidth,
            paddingTop + child.measuredHeight
          )
        }
      }
    }
    mFirstLayout = false
  }

  public override fun onSaveInstanceState(): Parcelable? {
    val superState = super.onSaveInstanceState()
    val ss =
      SavedState(
        superState!!
      )
    ss.position = mCurItem
    if (mAdapter != null) {
      ss.adapterState = mAdapter!!.saveState()
    }
    return ss
  }

  public override fun onRestoreInstanceState(state: Parcelable) {
    if (state !is SavedState) {
      super.onRestoreInstanceState(state)
      return
    }
    val ss = state
    super.onRestoreInstanceState(ss.superState)
    if (mAdapter != null) {
      mAdapter!!.restoreState(ss.adapterState, ss.loader)
      setCurrentItemInternal(ss.position, true)
    } else {
      mRestoredCurItem = ss.position
      mRestoredAdapterState = ss.adapterState
      mRestoredClassLoader = ss.loader
    }
  }

  /**
   * This is the persistent state that is saved by ViewPager.  Only needed
   * if you are creating a sublass of ViewPager that must save its own
   * state, in which case it should implement a subclass of this which
   * contains that state.
   */
  class SavedState : AbsSavedState {
    var position = 0
    var adapterState: Parcelable? = null
    var loader: ClassLoader? = null

    constructor(superState: Parcelable) : super(superState) {}

    override fun writeToParcel(out: Parcel, flags: Int) {
      super.writeToParcel(out, flags)
      out.writeInt(position)
      out.writeParcelable(adapterState, flags)
    }

    override fun toString(): String {
      return ("NonScrollableViewPager.SavedState{${Integer.toHexString(System.identityHashCode(this))}, position=  $position}")
    }

    internal constructor(`in`: Parcel, loader: ClassLoader?) : super(`in`, loader) {
      var loader = loader
      if (loader == null) {
        loader = javaClass.classLoader
      }
      position = `in`.readInt()
      adapterState = `in`.readParcelable(loader)
      this.loader = loader
    }

    companion object {
      @JvmField
      val CREATOR: Parcelable.Creator<SavedState> =
        object : ClassLoaderCreator<SavedState> {
          override fun createFromParcel(
            `in`: Parcel,
            loader: ClassLoader
          ): SavedState {
            return SavedState(
              `in`,
              loader
            )
          }

          override fun createFromParcel(`in`: Parcel): SavedState {
            return SavedState(
              `in`,
              null
            )
          }

          override fun newArray(size: Int): Array<SavedState?> {
            return arrayOfNulls(size)
          }
        }
    }
  }

  /**
   * Layout parameters that should be supplied for views added to a
   * ViewPager.
   */
  class LayoutParams : ViewGroup.LayoutParams {
    /**
     * true if data set changed and child needs its position to be updated
     */
    var needsPositionUpdate = false

    /**
     * true if this view was added during layout and needs to be measured
     * before being positioned.
     */
    var needsMeasure = false

    /**
     * Adapter position this view is for if !isDecor
     */
    var position = 0

    /**
     * Current child index within the ViewPager that this view occupies
     */
    var childIndex = 0

    constructor() : super(
      MATCH_PARENT,
      MATCH_PARENT
    ) {
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(
      context,
      attrs
    ) {
    }
  }
}