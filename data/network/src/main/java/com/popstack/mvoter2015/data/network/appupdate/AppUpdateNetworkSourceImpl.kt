package com.popstack.mvoter2015.data.network.appupdate

import android.content.Context
import com.popstack.mvoter2015.data.common.appupdate.AppUpdate
import com.popstack.mvoter2015.data.common.appupdate.AppUpdateNetworkSource
import com.popstack.mvoter2015.data.network.di.OkHttpProvider
import com.popstack.mvoter2015.data.network.helper.executeOrThrow
import okhttp3.MultipartBody
import okhttp3.Request
import org.json.JSONObject
import javax.inject.Inject

class AppUpdateNetworkSourceImpl @Inject constructor(private val context: Context) :
  AppUpdateNetworkSource {

  private val okHttpClient = OkHttpProvider.okHttpClient(context)

  override fun getLatestUpdate(deviceVersionCode: Long): AppUpdate {
    val requestBody = MultipartBody.Builder()
      .addFormDataPart("version_code", deviceVersionCode.toString())
      .build()

    val responseBody = okHttpClient.newCall(
      Request.Builder()
        .url("https://us-central1-maepaysoh2020.cloudfunctions.net/update/android")
        .post(requestBody)
        .build()
    )
      .executeOrThrow()

    val bodyReader = responseBody.charStream()
      .buffered()
    val jsonObject = JSONObject(bodyReader.readText())
    bodyReader.close()

    val data = jsonObject.getJSONObject("data")

    return AppUpdate(
      latestVersionCode = data.getLong("version_code"),
      requireForcedUpdate = data.getBoolean("is_force_update"),
      playStoreLink = data.getString("playstore_link"),
      selfHostedLink = data.getString("link")
    )
  }

}