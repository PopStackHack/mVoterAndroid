package com.popstack.mvoter2015.feature.image

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.provider.MediaStore
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.WindowInsets
import android.view.WindowInsetsController
import androidx.core.content.ContextCompat
import androidx.core.view.doOnLayout
import androidx.lifecycle.lifecycleScope
import coil.imageLoader
import coil.load
import coil.request.ImageRequest
import com.popstack.mvoter2015.R
import com.popstack.mvoter2015.core.BaseActivity
import com.popstack.mvoter2015.databinding.ActivityImageFullScreenViewBinding
import com.popstack.mvoter2015.helper.extensions.showShortToast
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.HttpUrl.Companion.toHttpUrl
import timber.log.Timber

class FullScreenImageViewActivity : BaseActivity<ActivityImageFullScreenViewBinding>() {

  companion object {
    private const val IE_IMAGE_URL = "image_url"

    fun intent(context: Context, imageUrl: String): Intent {
      val intent = Intent(context, FullScreenImageViewActivity::class.java)
      intent.putExtra(IE_IMAGE_URL, imageUrl)
      return intent
    }
  }

  private val imageUrl by lazy {
    intent.getStringExtra(IE_IMAGE_URL) ?: ""
  }

  override val binding by lazy {
    ActivityImageFullScreenViewBinding.inflate(layoutInflater)
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    setSupportActionBar(binding.toolBar)
    supportActionBar?.title = ""
    supportActionBar?.setDisplayHomeAsUpEnabled(true)

    binding.photoView.load(imageUrl)

    binding.photoView.setOnPhotoTapListener { view, x, y ->
      Timber.i("tapped")
      if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.R) {
        window.decorView.doOnLayout {
          if (it.rootWindowInsets.isVisible(WindowInsets.Type.statusBars())) {
            hideSystemUI()
          } else {
            showSystemUI()
          }
        }
      } else {
        hideSystemUI()
      }
    }

    window.navigationBarColor = Color.BLACK
    window.decorView.setOnSystemUiVisibilityChangeListener { visibility ->
      // Note that system bars will only be "visible" if none of the
      // LOW_PROFILE, HIDE_NAVIGATION, or FULLSCREEN flags are set.
      if (visibility and View.SYSTEM_UI_FLAG_FULLSCREEN == 0) {
        Timber.i("VISIBLE")
        supportActionBar?.show()
      } else {
        Timber.i("GONE")
        supportActionBar?.hide()
      }
    }

    window.decorView.doOnLayout {
      if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.R) {
        it.windowInsetsController?.systemBarsBehavior = WindowInsetsController.BEHAVIOR_SHOW_BARS_BY_TOUCH
      }
    }

  }

  override fun onSupportNavigateUp(): Boolean {
    onBackPressed()
    return true
  }

  override fun onCreateOptionsMenu(menu: Menu?): Boolean {
    menuInflater.inflate(R.menu.menu_image_view, menu)
    return true
  }

  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    if (item.itemId == R.id.action_save_image) {
      saveImageToGallery()
    }
    return super.onOptionsItemSelected(item)
  }

  //Copied from   https://github.com/coroutineDispatcher/pocket_treasure/blob/4154e5e5c0e8860939c3c60071a87cba12f984cb/gallery_module/src/main/java/com/sxhardha/gallery_module/image/FullImageFragment.kt#L227
  private fun saveImageToGallery() {
    lifecycleScope.launch {

      withContext(Dispatchers.IO) {
        val imageLoader = this@FullScreenImageViewActivity.imageLoader
        val request = ImageRequest.Builder(this@FullScreenImageViewActivity)
          .data(imageUrl)
          .build()
        val bitmap = withContext(Dispatchers.IO) {
          imageLoader.execute(request).drawable
        }?.let { drawable ->
          (drawable as BitmapDrawable).bitmap
        } ?: return@withContext

        val imagePath = imageUrl.toHttpUrl().pathSegments.last()
        val imageName = "${imagePath.substring(0, imagePath.indexOf("."))}"

        MediaStore.Images.Media.insertImage(contentResolver, bitmap, imageName, "")
        withContext(Dispatchers.Main) {
          showShortToast("Saved to Gallery")
        }
      }
    }
  }

  private fun hideSystemUI() {
    // Enables regular immersive mode.
    // For "lean back" mode, remove SYSTEM_UI_FLAG_IMMERSIVE.
    // Or for "sticky immersive," replace it with SYSTEM_UI_FLAG_IMMERSIVE_STICKY
    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.R) {
      window.decorView.doOnLayout {
        it.windowInsetsController?.hide(WindowInsets.Type.statusBars())
      }
    } else {
      window.decorView.systemUiVisibility = (
        // Set the content to appear under the system bars so that the
        // content doesn't resize when the system bars hide and show.
        View.SYSTEM_UI_FLAG_LAYOUT_STABLE
          or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
          or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
          // Hide the nav bar and status bar
          or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
          or View.SYSTEM_UI_FLAG_FULLSCREEN
        )
    }
  }

  // Shows the system bars by removing all the flags
// except for the ones that make the content appear under the system bars.
  private fun showSystemUI() {
    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.R) {
      window.decorView.doOnLayout {
//        it.windowInsetsController?.show(WindowInsets.Type.systemBars())
        it.windowInsetsController?.show(WindowInsets.Type.statusBars())
      }
    } else {
      window.decorView.systemUiVisibility = (
        View.SYSTEM_UI_FLAG_LAYOUT_STABLE
          or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
          or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        )
    }
  }

}