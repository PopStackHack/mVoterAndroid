package com.popstack.mvoter2015.helper.intent

import android.content.Intent
import androidx.core.net.toUri


object Intents {

  fun viewUrl(url: String): Intent {
    return Intent(Intent.ACTION_VIEW, url.toUri())
  }

  fun shareUrl(url: String): Intent {
    val shareIntent = Intent(Intent.ACTION_SEND)
    shareIntent.type = "text/plain"
    shareIntent.putExtra(Intent.EXTRA_TEXT, url)
    return shareIntent
  }
}