package com.popstack.mvoter2015.data.android.appupdate

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.aungkyawpaing.coroutinetestrule.CoroutineTestRule
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GoogleApiAvailability
import com.nhaarman.mockitokotlin2.mock
import com.popstack.mvoter2015.data.common.appupdate.AppUpdate
import com.popstack.mvoter2015.domain.infra.AppUpdateManager
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config
import org.robolectric.shadows.gms.Shadows
import org.robolectric.shadows.gms.common.ShadowGoogleApiAvailability

@RunWith(AndroidJUnit4::class)
@Config(manifest = Config.NONE, shadows = [ShadowGoogleApiAvailability::class])
class AndroidAppUpdateManagerTest {

  @get:Rule
  var coroutineTestRule = CoroutineTestRule()

  private val fakeAppVersionProvider = FakeAppVersionProvider()

  private val appUpdateManager = AndroidAppUpdateManager(
    context = ApplicationProvider.getApplicationContext() as Context,
    appUpdateCacheSource = mock(),
    appUpdateNetworkSource = mock(),
    appVersionProvider = fakeAppVersionProvider,
    dispatcherProvider = coroutineTestRule.testDispatcherProvider
  )

  @Before
  fun setUp() {
    val shadowGoogleApiAvailability = Shadows.shadowOf(GoogleApiAvailability.getInstance())
    val expectedCode = ConnectionResult.SUCCESS
    shadowGoogleApiAvailability.setIsGooglePlayServicesAvailable(expectedCode)
  }

  @Test
  fun processUpdateSameVersionCode() {

    fakeAppVersionProvider.versionCode = 0

    val input = AppUpdate(
      latestVersionCode = 0,
      requireForcedUpdate = false,
      playStoreLink = "",
      selfHostedLink = ""
    )

    val expected = AppUpdateManager.UpdateResult.NotRequired
    val actual = appUpdateManager.processLatestAppUpdate(input)

    Assert.assertEquals(expected, actual)
  }

  @Test
  fun processUpdateLowerVersionCode() {

    fakeAppVersionProvider.versionCode = 1

    val input = AppUpdate(
      latestVersionCode = 0,
      requireForcedUpdate = false,
      playStoreLink = "",
      selfHostedLink = ""
    )

    val expected = AppUpdateManager.UpdateResult.NotRequired
    val actual = appUpdateManager.processLatestAppUpdate(input)

    Assert.assertEquals(expected, actual)
  }

  @Test
  fun processUpdateRequiredButNotForced() {
    fakeAppVersionProvider.versionCode = 1

    val input = AppUpdate(
      latestVersionCode = 2,
      requireForcedUpdate = false,
      playStoreLink = "playStore",
      selfHostedLink = "selfHosted"
    )

    val expected = AppUpdateManager.UpdateResult.RelaxedUpdate(input.playStoreLink)
    val actual = appUpdateManager.processLatestAppUpdate(input)

    Assert.assertEquals(expected, actual)
  }

  @Test
  fun processUpdateRequiredAndForced() {
    fakeAppVersionProvider.versionCode = 1

    val input = AppUpdate(
      latestVersionCode = 2,
      requireForcedUpdate = true,
      playStoreLink = "playStore",
      selfHostedLink = "selfHosted"
    )

    val expected = AppUpdateManager.UpdateResult.ForcedUpdate(input.playStoreLink)
    val actual = appUpdateManager.processLatestAppUpdate(input)

    Assert.assertEquals(expected, actual)
  }

  @Test
  fun getDownloadLinkPlayStoreAvailable() {
    val shadowGoogleApiAvailability = Shadows.shadowOf(GoogleApiAvailability.getInstance())
    val expectedCode = ConnectionResult.SUCCESS
    shadowGoogleApiAvailability.setIsGooglePlayServicesAvailable(expectedCode)

    val input = AppUpdate(
      latestVersionCode = 0,
      requireForcedUpdate = false,
      playStoreLink = "playStore",
      selfHostedLink = "selfHosted"
    )

    val expected = "playStore"
    val actual = appUpdateManager.getDownloadLink(input)

    Assert.assertEquals(expected, actual)
  }

  @Test
  fun getDownloadLinkPlayStoreUnavailable() {
    val shadowGoogleApiAvailability = Shadows.shadowOf(GoogleApiAvailability.getInstance())
    val expectedCode = ConnectionResult.SERVICE_MISSING
    shadowGoogleApiAvailability.setIsGooglePlayServicesAvailable(expectedCode)

    val input = AppUpdate(
      latestVersionCode = 0,
      requireForcedUpdate = false,
      playStoreLink = "playStore",
      selfHostedLink = "selfHosted"
    )

    val expected = "selfHosted"
    val actual = appUpdateManager.getDownloadLink(input)

    Assert.assertEquals(expected, actual)
  }

}