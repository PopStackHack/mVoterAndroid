package com.popstack.mvoter2015.feature.faq

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContract
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.popstack.mvoter2015.R
import com.popstack.mvoter2015.core.BaseActivity
import com.popstack.mvoter2015.databinding.ActivityFaqCategorySelectBinding
import com.popstack.mvoter2015.domain.faq.model.FaqCategory
import com.popstack.mvoter2015.feature.faq.ballot.displayString
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class FaqCategorySelectActivity : BaseActivity<ActivityFaqCategorySelectBinding>() {

  companion object {
    private const val ARG_SELECTED_CATEGORY = "selected_category"
    private const val ANIMATION_DELAY_MILI = 200L
  }

  private val previouslySelectedCategory: FaqCategory by lazy {
    FaqCategory.valueOf(intent.getStringExtra(ARG_SELECTED_CATEGORY)!!)
  }

  override val binding: ActivityFaqCategorySelectBinding by lazy {
    ActivityFaqCategorySelectBinding.inflate(layoutInflater)
  }

  private val faqCategoryRecyclerViewAdapter =
    FaqCategoryRecyclerViewAdapter(onClickCategory = this::handleCategorySelect)

  private fun handleCategorySelect(faqCategory: FaqCategory) {
    lifecycleScope.launch {
      binding.tvSelectedCategory.text = previouslySelectedCategory.displayString(this@FaqCategorySelectActivity)
      binding.rvFaqCategory.isVisible = false
      delay(ANIMATION_DELAY_MILI)
      val intent = Intent()
      intent.putExtra(ARG_SELECTED_CATEGORY, faqCategory.toString())
      setResult(Activity.RESULT_OK, intent)
      finish()
    }
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setSupportActionBar(binding.toolBar)
    supportActionBar?.title = this.getString(R.string.title_info)

    binding.rvFaqCategory.apply {
      adapter = faqCategoryRecyclerViewAdapter
      layoutManager = LinearLayoutManager(this@FaqCategorySelectActivity)
    }

    binding.tvSelectedCategory.text = previouslySelectedCategory.displayString(this)

    binding.viewTransparentLayer.setOnClickListener {
      setResult(Activity.RESULT_CANCELED)
      finish()
    }

    lifecycleScope.launch {
      delay(ANIMATION_DELAY_MILI)
      binding.rvFaqCategory.isVisible = true
    }

    faqCategoryRecyclerViewAdapter.setSelectedCategory(previouslySelectedCategory)
  }

  override fun onPause() {
    super.onPause()
    overridePendingTransition(0, 0)
  }

  /**
   * Activity Result Contract for easier access
   */
  class SelectFaqCategoryContract :
    ActivityResultContract<FaqCategory, FaqCategory?>() {

    override fun createIntent(context: Context, input: FaqCategory): Intent {
      val intent = Intent(context, FaqCategorySelectActivity::class.java)
      intent.putExtra(ARG_SELECTED_CATEGORY, input.toString())
      return intent
    }

    override fun parseResult(resultCode: Int, intent: Intent?): FaqCategory? {
      if (resultCode == Activity.RESULT_OK) {
        val category = intent?.getStringExtra(ARG_SELECTED_CATEGORY) ?: return null
        return FaqCategory.valueOf(category)
      }
      return null
    }

  }

}