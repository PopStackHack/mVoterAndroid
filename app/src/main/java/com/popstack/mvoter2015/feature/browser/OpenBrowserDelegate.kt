package com.popstack.mvoter2015.feature.browser

import com.popstack.mvoter2015.feature.settings.AppSettings
import javax.inject.Inject

class OpenBrowserDelegate @Inject constructor(
  private val appSettings: AppSettings
) {

  fun browserHandler(): OpenBrowserHandler {
    return if (appSettings.getUseExternalBrowser()) {
      ExternalBrowserHandler()
    } else {
      InAppBrowserHandler()
    }
  }
}