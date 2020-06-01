package com.popstack.mvoter2015.feature

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bluelinelabs.conductor.Conductor
import com.bluelinelabs.conductor.Router
import com.bluelinelabs.conductor.RouterTransaction
import com.popstack.mvoter2015.databinding.ActivityHostBinding
import com.popstack.mvoter2015.di.Injectable
import com.popstack.mvoter2015.di.conductor.InjectionControllerChangeListener
import com.popstack.mvoter2015.feature.home.HomeController

//A simple activity that host Conductor's FrameLayout
class HostActivity : AppCompatActivity(), Injectable, HasRouter {

  private val binding by lazy {
    ActivityHostBinding.inflate(layoutInflater)
  }

  private lateinit var router: Router

  private val injectionControllerChangeListener by lazy {
    InjectionControllerChangeListener()
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(binding.root)

    router = Conductor.attachRouter(this, binding.container, savedInstanceState)
    router.addChangeListener(injectionControllerChangeListener)

    if (!router.hasRootController()) {
      //Set your first routing here
      val homeController = HomeController()
      router.pushController(RouterTransaction.with(homeController))
    }
  }

  override fun onDestroy() {
    router.removeChangeListener(injectionControllerChangeListener)
    super.onDestroy()
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