package com.popstack.mvoter2015.feature.candidate.listing

import com.bluelinelabs.conductor.Router

/**
 * A static variable that should not be used
 * But used for easier access to parent router so we can navigate to detail page from pager's child page
 */
internal object CandidateListPagerParentRouter {

  var router: Router? = null

  fun setParentRouter(router: Router) {
    this.router = router
  }

  fun destroy() {
    router = null
  }
}