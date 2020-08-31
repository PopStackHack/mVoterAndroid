package com.popstack.mvoter2015.feature

import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bluelinelabs.conductor.Conductor
import com.bluelinelabs.conductor.Router
import com.bluelinelabs.conductor.RouterTransaction
import com.popstack.mvoter2015.databinding.ActivityHostBinding
import com.popstack.mvoter2015.domain.party.model.PartyId
import com.popstack.mvoter2015.feature.party.detail.PartyDetailController
import com.popstack.mvoter2015.feature.splash.SplashController
import com.popstack.mvoter2015.logging.BreadcrumbControllerChangeHandler
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

//A simple activity that host Conductor's FrameLayout
@AndroidEntryPoint
class HostActivity : AppCompatActivity(), HasRouter {

  private val binding by lazy {
    ActivityHostBinding.inflate(layoutInflater)
  }

  private lateinit var router: Router

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(binding.root)

    router = Conductor.attachRouter(this, binding.container, savedInstanceState)
    router.addChangeListener(BreadcrumbControllerChangeHandler)

    var handledDeepLink = false
    intent.data?.let { deepLinkUri ->
      Timber.d("Coming from deeplink url: ${deepLinkUri.host}${deepLinkUri.path}")
      handledDeepLink = handleDeepLink(deepLinkUri)
    }

    if (!router.hasRootController() && handledDeepLink.not()) {
      //Set your first routing here
      router.pushController(RouterTransaction.with(SplashController()))
    }
  }

  private fun handleDeepLink(deepLinkUri: Uri): Boolean {
    val host = deepLinkUri.host ?: ""
    val path = deepLinkUri.path ?: ""

    Timber.i("deep link recieved. host is $host, path is $path")

    //Handle parties deep link
    if (host == "parties" || (path.matches(Regex("/parties/\\d+")) && host == "web.mvoterapp.com")) {
      val partyId = PartyId(deepLinkUri.lastPathSegment ?: return false)
      val partyDetailController = PartyDetailController.newInstance(partyId)

      //Existing stack so we just push on top
      if (router.hasRootController()) {
        router.pushController(RouterTransaction.with(partyDetailController))
      } else {
        //No existing stack, we recreate the stack
        router.setBackstack(
          listOf(
            RouterTransaction.with(SplashController()),
            RouterTransaction.with(partyDetailController)
          ), null)
      }

      return true
    }

    return false
  }

  override fun onDestroy() {
    super.onDestroy()
    router.removeChangeListener(BreadcrumbControllerChangeHandler)
  }

  override fun onBackPressed() {
    if (!router.handleBack()) {
      super.onBackPressed()
    }
  }

  override fun onSupportNavigateUp(): Boolean {
    if (!router.handleBack()) {
      return super.onSupportNavigateUp()
    }
    return true
  }

  override fun router(): Router {
    return router
  }

}