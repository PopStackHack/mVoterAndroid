object BuildConfig {
  const val compileSdk = 30
  const val minSdk = 21
  const val targetSdk = 30

  private const val versionMajor = 4
  private const val versionMinor = 0
  private const val versionPatch = 0
  private const val versionBuild = 5

  const val versionName =
    "$versionMajor.$versionMinor.$versionPatch"
  const val versionCode =
    versionMajor * 1000000 + versionMinor * 10000 + versionPatch * 100 + versionBuild

}

object CommonLibs {
  const val android_gradle_plugin = "com.android.tools.build:gradle:4.0.1"

  const val timber = "com.jakewharton.timber:timber:4.7.1"
  const val junit = "junit:junit:4.13"
  const val javaxInject = "javax.inject:javax.inject:1"
  const val javaXAnnotations = "org.glassfish:javax.annotation:10.0-b28"

  const val circle_image_view = "de.hdodenhof:circleimageview:3.0.1"

  const val mm_ph_number = "com.aungkyawpaing.mmphonenumber:mmphonenumber:1.1.0"
}

//region AndroidX
object AndroidXAppCompat {
  const val app_compat = "androidx.appcompat:appcompat:1.3.0-alpha02"
}

object AndroidXRecyclerView {
  private const val version = "1.2.0-alpha05"

  const val recycler_view = "androidx.recyclerview:recyclerview:$version"
  const val selection = "androidx.recyclerview:recyclerview-selection:$version"
}

object AndroidXCardView {
  const val card_view = "androidx.cardview:cardview:1.0.0"
}

object AndroidXConstraintLayout {
  private const val version = "2.0.0"

  const val constraint_layout = "androidx.constraintlayout:constraintlayout:$version"
}

object AndroidXViewPager {
  const val view_pager = "androidx.viewpager:viewpager:1.0.0"

  const val view_pager_2 = "androidx.viewpager2:viewpager2:1.1.0-alpha01"
}

object AndroidXSqlite {
  private const val version = "2.1.0"

  const val sqlite = "androidx.sqlite:sqlite:$version"
  const val sqlite_framework = "androidx.sqlite:sqlite-framework:$version"
  const val sqlite_ktx = "androidx.sqlite:sqlite-ktx:$version"
}

object AndroidArchWork {
  private const val version = "2.4.0"

  const val work_runtime = "androidx.work:work-runtime:$version"
  const val work_runtime_ktx = "androidx.work:work-runtime-ktx:$version"
  const val work_testing = "androidx.work:work-testing:$version"

}

object AndroidXCore {
  private const val version = "1.5.0-alpha02"

  const val core = "androidx.core:core:$version"
  const val core_ktx = "androidx.core:core-ktx:$version"
}

object AndroidXPaging {
  private const val version = "3.0.0-alpha06"

  const val common = "androidx.paging:paging-common:$version"
  const val runtime = "androidx.paging:paging-runtime:$version"
}

//endregion
object Material {
  const val material = "com.google.android.material:material:1.3.0-alpha02"
}

object Coil {
  const val coil = "io.coil-kt:coil:0.9.5"
}

object Conductor {
  private const val version = "3.0.0-rc5"

  const val core = "com.bluelinelabs:conductor:$version"
  const val androidx_transition = "com.bluelinelabs:conductor-androidx-transition:$version"
  const val viewpager = "com.bluelinelabs:conductor-viewpager:$version"
  const val lifecycle = "com.bluelinelabs:conductor-archlifecycle:$version"
}

object Firebase {
  const val messaging = "com.google.firebase:firebase-messaging:20.2.4"
  const val analytics = "com.google.firebase:firebase-analytics-ktx:17.5.0"

  const val crashlytics = "com.google.firebase:firebase-crashlytics-ktx:17.2.1"
  const val crashlytics_plugin = "com.google.firebase:firebase-crashlytics-gradle:2.2.0"
}

object GoogleService {

  const val gms_plugin = "com.google.gms:google-services:4.3.3"
  const val auth = "com.google.android.gms:play-services-auth:16.0.1"
  const val ads = "com.google.android.gms:play-services-ads:17.1.2"
  const val consent = "com.google.android.ads.consent:consent-library:1.0.6"
  const val map = "com.google.android.gms:play-services-maps:16.1.0"
  const val location = "com.google.android.gms:play-services-location:16.0.0"
}

object Kakao {
  private const val version = "2.2.0"

  const val core = "com.agoda.kakao:kakao:$version"
}

object Kotlin {
  private const val version = "1.4.0"

  const val stdblib_jdk = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:$version"
  const val gradle_plugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:$version"
}

object KotlinCoroutine {
  private const val version = "1.3.9"

  const val core = "org.jetbrains.kotlinx:kotlinx-coroutines-core:$version"
  const val android = "org.jetbrains.kotlinx:kotlinx-coroutines-android:$version"
  const val adapter = "com.jakewharton.retrofit:retrofit2-kotlin-coroutines-adapter:0.9.2"
  const val test = "org.jetbrains.kotlinx:kotlinx-coroutines-test:$version"
}

object KtLint {
  const val version = "9.3.0"
  const val name = "org.jlleitschuh.gradle.ktlint"

  const val plugin = "org.jlleitschuh.gradle:ktlint-gradle:$version"
}

object LeakCanary {
  const val android = "com.squareup.leakcanary:leakcanary-android:2.0-beta-3"
}

object Lottie {
  private const val version = "3.2.2"

  const val android = "com.airbnb.android:lottie:$version"
}

object Monex {
  private const val version = "0.4.3"

  const val monex = "com.aungkyawpaing.monex:monex:$version"
  const val no_op = "com.aungkyawpaing.monex:monex-no-op:$version"
}

object OkHttp {
  private const val version = "4.8.1"

  const val client = "com.squareup.okhttp3:okhttp:$version"
  const val logger = "com.squareup.okhttp3:logging-interceptor:$version"
  const val mock_web_server = "com.squareup.okhttp3:mockwebserver:$version"
}

object Retrofit {
  private const val version = "2.9.0"

  const val core = "com.squareup.retrofit2:retrofit:$version"
  const val moshi_converter = "com.squareup.retrofit2:converter-moshi:$version"
}

object SqlDelight {
  private const val version = "1.4.3"

  const val gradle_plugin = "com.squareup.sqldelight:gradle-plugin:$version"
  const val android_driver = "com.squareup.sqldelight:android-driver:$version"
  const val coroutine_extension = "com.squareup.sqldelight:coroutines-extensions:$version"
  const val runtime = "com.squareup.sqldelight:runtime-common::$version"
  const val jvm_driver = "com.squareup.sqldelight:sqlite-driver:$version"
}

object Shimmer {
  const val reycler_view = "com.github.sharish:ShimmerRecyclerView:v1.3"
}

object Wormhole {
  private const val version = "0.3.1"

  const val gradle_plugin = "com.jakewharton.wormhole:wormhole-gradle:$version"

}
