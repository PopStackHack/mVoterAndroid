package com.popstack.mvoter2015.feature

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bluelinelabs.conductor.Conductor
import com.bluelinelabs.conductor.Router
import com.bluelinelabs.conductor.RouterTransaction
import com.popstack.mvoter2015.databinding.ActivityHostBinding
import com.popstack.mvoter2015.feature.home.HomeController
import dagger.hilt.android.AndroidEntryPoint

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

    if (!router.hasRootController()) {
      //Set your first routing here
      val homeController = HomeController()
      router.pushController(RouterTransaction.with(homeController))
    }
  }

  override fun onDestroy() {
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