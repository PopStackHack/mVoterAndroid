package com.popstack.mvoter2015.core.mvp

interface Presentable<V : Viewable> {

  /**
   * Every Presentable must attach a Viewable
   *
   * @param viewable Viewable
   */
  fun attachView(viewable: V)

  /**
   * Every Presentable must detach its Viewable
   */
  fun detachView()

}