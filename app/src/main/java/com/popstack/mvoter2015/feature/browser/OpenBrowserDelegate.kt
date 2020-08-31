package com.popstack.mvoter2015.feature.browser

import android.content.Context
import com.popstack.mvoter2015.feature.settings.AppSettingsEntryPoint
import dagger.hilt.EntryPoints

class OpenBrowserDelegate(private val context: Context) {

  fun browserHandler(): OpenBrowserHandler {
    return if (EntryPoints.get(context, AppSettingsEntryPoint::class.java).appSettings().getUseExternalBrowser()) {
      ExternalBrowserHandler()
    } else {
      InAppBrowserHandler()
    }
  }
}