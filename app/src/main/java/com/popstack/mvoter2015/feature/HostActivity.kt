package com.popstack.mvoter2015.feature

import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.lifecycleScope
import com.bluelinelabs.conductor.Conductor
import com.bluelinelabs.conductor.Router
import com.bluelinelabs.conductor.RouterTransaction
import com.popstack.mvoter2015.R
import com.popstack.mvoter2015.databinding.ActivityHostBinding
import com.popstack.mvoter2015.di.Injectable
import com.popstack.mvoter2015.di.conductor.ControllerInjectorChangeHandler
import com.popstack.mvoter2015.domain.candidate.model.CandidateId
import com.popstack.mvoter2015.domain.infra.AppUpdateManager
import com.popstack.mvoter2015.domain.party.model.PartyId
import com.popstack.mvoter2015.feature.appupdate.RelaxedAppUpdateBottomSheet
import com.popstack.mvoter2015.feature.candidate.detail.CandidateDetailController
import com.popstack.mvoter2015.feature.party.detail.PartyDetailController
import com.popstack.mvoter2015.feature.settings.AppSettings
import com.popstack.mvoter2015.feature.settings.AppTheme
import com.popstack.mvoter2015.feature.splash.SplashController
import com.popstack.mvoter2015.helper.intent.Intents
import com.popstack.mvoter2015.logging.BreadcrumbControllerChangeHandler
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

//A simple activity that host Conductor's FrameLayout
class HostActivity : AppCompatActivity(), HasRouter, Injectable, HasAndroidInjector {

  private val binding by lazy {
    ActivityHostBinding.inflate(layoutInflater)
  }

  @Inject
  lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Any>

  @Inject
  lateinit var appSettings: AppSettings

  @Inject
  lateinit var appUpdateManager: AppUpdateManager

  private lateinit var router: Router

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(binding.root)

    router = Conductor.attachRouter(this, binding.container, savedInstanceState)
    router.addChangeListener(BreadcrumbControllerChangeHandler)
    router.addChangeListener(ControllerInjectorChangeHandler)

    var handledDeepLink = false
    intent.data?.let { deepLinkUri ->
      Timber.d("Coming from deeplink url: ${deepLinkUri.host}${deepLinkUri.path}")
      handledDeepLink = handleDeepLink(deepLinkUri)
    }

    if (!router.hasRootController() && handledDeepLink.not()) {
      //Set your first routing here
      router.pushController(RouterTransaction.with(SplashController()))
    }

    when (appSettings.getTheme()) {
      AppTheme.SYSTEM_DEFAULT -> {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
      }
      AppTheme.LIGHT -> {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
      }
      AppTheme.DARK -> {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
      }
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
          ),
          null
        )
      }

      return true
    } else if (host == "candidates" || (path.matches(Regex("/candidates/\\d+")) && host == "web.mvoterapp.com")) {
      val candidateId = CandidateId(deepLinkUri.lastPathSegment ?: return false)
      val candidateDetailController = CandidateDetailController.newInstance(candidateId)

      //Existing stack so we just push on top
      if (router.hasRootController()) {
        router.pushController(RouterTransaction.with(candidateDetailController))
      } else {
        //No existing stack, we recreate the stack
        router.setBackstack(
          listOf(
            RouterTransaction.with(SplashController()),
            RouterTransaction.with(candidateDetailController)
          ),
          null
        )
      }

      return true
    }

    return false
  }

  private var hasRelaxedUpdateShownBefore = false

  private fun launchAppUpdate() {
    lifecycleScope.launch {
      when (val appUpdateResult = appUpdateManager.checkForUpdate()) {
        is AppUpdateManager.UpdateResult.ForcedUpdate -> {
          AlertDialog.Builder(this@HostActivity)
            .setTitle(R.string.update_required)
            .setMessage(R.string.update_required_message)
            .setPositiveButton(R.string.ok) { dialog, _ ->
              dialog.dismiss()
            }
            .setOnDismissListener {
              startActivity(Intents.viewUrl(appUpdateResult.updateLink))
            }.create()
            .show()
        }
        is AppUpdateManager.UpdateResult.RelaxedUpdate -> {
          if (!hasRelaxedUpdateShownBefore) {
            val relaxedUpdateSheet = RelaxedAppUpdateBottomSheet()
            relaxedUpdateSheet.onOkayClick = {
              startActivity(Intents.viewUrl(appUpdateResult.updateLink))
              relaxedUpdateSheet.dismiss()
            }
            relaxedUpdateSheet.onCancelClick = {
              relaxedUpdateSheet.dismiss()
            }
            relaxedUpdateSheet.show(supportFragmentManager, "Relaxed_Update")
            hasRelaxedUpdateShownBefore = true
          }
        }
      }
    }
  }

  override fun onDestroy() {
    super.onDestroy()
    router.removeChangeListener(BreadcrumbControllerChangeHandler)
    router.removeChangeListener(ControllerInjectorChangeHandler)
  }

  override fun onBackPressed() {
    if (!router.handleBack()) {
      super.onBackPressed()
    }
  }

  override fun onResume() {
    super.onResume()
    launchAppUpdate()
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

  override fun androidInjector(): AndroidInjector<Any> {
    return dispatchingAndroidInjector
  }

}