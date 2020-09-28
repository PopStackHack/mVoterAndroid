package com.popstack.mvoter2015.feature.faq.ballot

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContract
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.popstack.mvoter2015.core.BaseActivity
import com.popstack.mvoter2015.databinding.ActivityBallotCategorySelectBinding
import com.popstack.mvoter2015.domain.faq.model.BallotExampleCategory
import com.popstack.mvoter2015.feature.faq.displayString
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class BallotExampleSelectActivity : BaseActivity<ActivityBallotCategorySelectBinding>() {

  companion object {
    private const val ARG_SELECTED_CATEGORY = "selected_category"
    private const val ANIMATION_DELAY_MILI = 200L
  }

  private val previouslySelectedCategory: BallotExampleCategory by lazy {
    BallotExampleCategory.valueOf(intent.getStringExtra(ARG_SELECTED_CATEGORY)!!)
  }

  override val binding: ActivityBallotCategorySelectBinding by lazy {
    ActivityBallotCategorySelectBinding.inflate(layoutInflater)
  }

  private val ballotCategoryRecyclerViewAdapter =
    BallotCategoryRecyclerViewAdapter(onClickCategory = this::handleCategorySelect)

  private fun handleCategorySelect(ballotExampleCategory: BallotExampleCategory) {
    lifecycleScope.launch {
      binding.tvSelectedCategory.text = previouslySelectedCategory.displayString(this@BallotExampleSelectActivity)
      binding.rvBallotCategory.isVisible = false
      delay(ANIMATION_DELAY_MILI)
      val intent = Intent()
      intent.putExtra(ARG_SELECTED_CATEGORY, ballotExampleCategory.toString())
      setResult(Activity.RESULT_OK, intent)
      finish()
    }
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setSupportActionBar(binding.toolBar)
    supportActionBar?.title = ""

    binding.rvBallotCategory.apply {
      adapter = ballotCategoryRecyclerViewAdapter
      layoutManager = LinearLayoutManager(this@BallotExampleSelectActivity)
    }

    binding.tvSelectedCategory.text = previouslySelectedCategory.displayString(this)

    binding.viewTransparentLayer.setOnClickListener {
      setResult(Activity.RESULT_CANCELED)
      finish()
    }

    lifecycleScope.launch {
      delay(ANIMATION_DELAY_MILI)
      binding.rvBallotCategory.isVisible = true
    }

    ballotCategoryRecyclerViewAdapter.setSelectedCategory(previouslySelectedCategory)
  }

  override fun onPause() {
    super.onPause()
    overridePendingTransition(0, 0)
  }

  /**
   * Activity Result Contract for easier access
   */
  class SelectBallotCategoryContract :
    ActivityResultContract<BallotExampleCategory, BallotExampleCategory?>() {

    override fun createIntent(context: Context, input: BallotExampleCategory): Intent {
      val intent = Intent(context, BallotExampleSelectActivity::class.java)
      intent.putExtra(ARG_SELECTED_CATEGORY, input.toString())
      return intent
    }

    override fun parseResult(resultCode: Int, intent: Intent?): BallotExampleCategory? {
      if (resultCode == Activity.RESULT_OK) {
        val category = intent?.getStringExtra(ARG_SELECTED_CATEGORY) ?: return null
        return BallotExampleCategory.valueOf(category)
      }
      return null
    }

  }

}