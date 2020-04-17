object BuildConfig {
  const val compileSdk = 29
  const val minSdk = 21
  const val targetSdk = 29

  private const val versionMajor = 4
  private const val versionMinor = 0
  private const val versionPatch = 0
  private const val versionBuild = 0

  const val versionName =
    "$versionMajor.$versionMinor.$versionPatch"
  const val versionCode =
    versionMajor * 1000000 + versionMinor * 10000 + versionPatch * 100 + versionBuild

}

object CommonLibs {
  const val android_gradle_plugin = "com.android.tools.build:gradle:4.0.0-beta04"

  const val dexter = "com.karumi:dexter:5.0.0"

  const val phrase = "com.squareup.phrase:phrase:1.1.0"
  const val sonar = "com.facebook.sonar:sonar:0.0.1"
  const val timber = "com.jakewharton.timber:timber:4.7.1"
  const val junit = "junit:junit:4.12"
  const val javaxInject = "javax.inject:javax.inject:1"
  const val javaXAnnotations = "org.glassfish:javax.annotation:10.0-b28"

  const val easy_image = "com.github.jkwiecien:EasyImage:3.0.3"
  const val android_crop = "com.soundcloud.android:android-crop:1.0.1@aar"
  const val ucrop = "com.github.yalantis:ucrop:2.2.2"
  const val fastscroll = "com.simplecityapps:recyclerview-fastscroll:2.0.0"

  const val colorpicker = "com.github.QuadFlask:colorpicker:0.0.13"
  const val circle_image_view = "de.hdodenhof:circleimageview:3.0.1"
  const val shape_of_views = "com.github.florent37:shapeofview:1.4.7"

  const val burtha = "com.aungkyawpaing.burtha:burtha-kt:1.1.1"

}

//region AndroidX
object AndroidXAnnotations {
  const val annotations = "androidx.annotation:annotation:1.1.0"
}

object AndroidXAppCompat {
  const val app_compat = "androidx.appcompat:appcompat:1.2.0-alpha03"
}

object AndroidXRecyclerView {
  private const val version = "1.2.0-alpha02"

  const val recycler_view = "androidx.recyclerview:recyclerview:$version"
  const val selection = "androidx.recyclerview:recyclerview-selection:$version"
}

object AndroidXCardView {
  const val card_view = "androidx.cardview:cardview:1.0.0"
}

object AndroidXConstraintLayout {
  private const val version = "2.0.0-beta4"

  const val constraint_layout = "androidx.constraintlayout:constraintlayout:$version"
}

object AndroidXViewPager {
  const val view_pager = "androidx.viewpager:viewpager:1.0.0"

  const val view_pager_2 = "androidx.viewpager2:viewpager2:1.1.0-alpha01"
}

object AndroidXLegacy {
  private const val version = "1.0.0"

  const val support_v4 = "androidx.legacy:legacy-support-v4:$version"
}

object AndroidXPalette {
  const val palette = "androidx.palette:palette:1.0.0"
}

object AndroidXPreference {
  private const val version = "1.1.0"

  const val preference = "androidx.preference:preference-ktx:$version"
  const val preference_ktx = "androidx.preference:preference-ktx:$version"
}

object AndroidXSecurity {
  private const val version = "1.0.0-alpha02"

  const val crypto = "androidx.security:security-crypto:$version"
}

object AndroidXSqlite {
  private const val version = "2.1.0-alpha01"

  const val sqlite = "androidx.sqlite:sqlite:$version"
  const val sqlite_framework = "androidx.sqlite:sqlite-framework:$version"
  const val sqlite_ktx = "androidx.sqlite:sqlite-ktx:$version"
}

object AndroidArchWork {
  private const val version = "2.3.0-rc01"

  const val work_runtime = "androidx.work:work-runtime:$version"
  const val work_runtime_ktx = "androidx.work:work-runtime-ktx:$version"
  const val work_testing = "androidx.work:work-testing:$version"

}

object AndroidXCore {
  private const val version = "1.2.0-beta02"

  const val core = "androidx.core:core:$version"
  const val core_ktx = "androidx.core:core-ktx:$version"
}

object AndroidXLocalBroadcastManager {

  const val local_broadcast_manager =
    "androidx.localbroadcastmanager:localbroadcastmanager:1.1.0-alpha01"

}

//endregion

object AssistedInject {

  private const val version = "0.5.2"

  const val annotations_dagger = "com.squareup.inject:assisted-inject-annotations-dagger2:$version"
  const val processor_dagger = "com.squareup.inject:assisted-inject-processor-dagger2:$version"
}

object Material {
  const val material = "com.google.android.material:material:1.2.0-alpha02"
}

object Caligraphy {
  const val caligraphy = "io.github.inflationx:calligraphy3:3.1.1"
  const val viewpump = "io.github.inflationx:viewpump:2.0.3"
}

object Coil {
  const val coil = "io.coil-kt:coil:0.9.5"
}

object Chuck {
  private const val version = "1.1.0"

  const val debug = "com.readystatesoftware.chuck:library:$version"
  const val no_op = "com.readystatesoftware.chuck:library-no-op:$version"
}

object Detekt {
  private const val version = "1.7.4"

  const val plugin = "io.gitlab.arturbosch.detekt:detekt-gradle-plugin:$version"

}

object EverNoteJob {
  private const val version = "1.2.6"

  const val core = "com.evernote:android-job:$version"
}

object Firebase {
  private const val version = "17.2.0"

  const val auth = "com.google.firebase:firebase-auth:$version"
  const val core = "com.google.firebase:firebase-core:$version"
  const val db = "com.google.firebase:firebase-database:$version"
  const val messaging = "com.google.firebase:firebase-messaging:17.3.4"
  const val crashlytics = "com.crashlytics.sdk.android:crashlytics:2.10.1"
  const val fabric_plugin = "io.fabric.tools:gradle:1.31.2"
  const val firebase_crashlytics_plugin =
    "com.google.firebase:firebase-crashlytics-gradle:2.0.0-beta01"
}

object Flipper {
  private const val version = "0.25.0"

  const val flipper = "com.facebook.flipper:flipper:$version"
  const val soloader = "com.facebook.soloader:soloader:0.5.1"
  const val network_plugin = "com.facebook.flipper:flipper-network-plugin:$version"
  const val image_plugin = "com.facebook.flipper:flipper-fresco-plugin:$version"

  const val filpper_no_op = "com.facebook.flipper:flipper-noop:$version"
}

object Glide {
  private const val version = "4.10.0"

  const val runtime = "com.github.bumptech.glide:glide:$version"
  const val compiler = "com.github.bumptech.glide:compiler:$version"
  const val transformations = "jp.wasabeef:glide-transformations:4.0.1"
}

object GoogleService {

  const val auth = "com.google.android.gms:play-services-auth:16.0.1"
  const val ads = "com.google.android.gms:play-services-ads:17.1.2"
  const val consent = "com.google.android.ads.consent:consent-library:1.0.6"
  const val gms = "com.google.gms:google-services:4.3.3"
  const val map = "com.google.android.gms:play-services-maps:16.1.0"
  const val location = "com.google.android.gms:play-services-location:16.0.0"
}

object Kakao {
  private const val version = "2.2.0"

  const val core = "com.agoda.kakao:kakao:$version"
}

object Kotlin {
  private const val version = "1.3.72"

  const val stdblib_jdk = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:$version"
  const val gradle_plugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:$version"
}

object KotlinCoroutine {
  private const val version = "1.3.2"

  const val core = "org.jetbrains.kotlinx:kotlinx-coroutines-core:$version"
  const val android = "org.jetbrains.kotlinx:kotlinx-coroutines-android:$version"
  const val adapter = "com.jakewharton.retrofit:retrofit2-kotlin-coroutines-adapter:0.9.2"
  const val test = "org.jetbrains.kotlinx:kotlinx-coroutines-test:$version"
}

object KtLint {
  const val version = "9.2.1"
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
  private const val version = "0.2.2"

  const val monex = "com.aungkyawpaing.monex:monex:$version"
  const val no_op = "com.aungkyawpaing.monex:monex-no-op:$version"
}

object OkHttp {
  private const val version = "4.5.0"

  const val client = "com.squareup.okhttp3:okhttp:$version"
  const val logger = "com.squareup.okhttp3:logging-interceptor:$version"
  const val mock_web_server = "com.squareup.okhttp3:mockwebserver:$version"
}

object Retrofit {
  private const val version = "2.8.1"

  const val core = "com.squareup.retrofit2:retrofit:$version"
  const val moshi_converter = "com.squareup.retrofit2:converter-moshi:$version"
}

object Renderer {

  const val renderer = "com.github.pedrovgs:renderers:3.4.0"
}

object SqlDelight {
  private const val version = "1.3.0"

  const val gradle_plugin = "com.squareup.sqldelight:gradle-plugin:$version"
  const val android_driver = "com.squareup.sqldelight:android-driver:$version"
  const val runtime = "com.squareup.sqldelight:runtime-common::$version"
  const val jvm_driver = "com.squareup.sqldelight:sqlite-driver:$version"
}

object Stetho {
  private const val version = "1.5.1"

  const val core = "com.facebook.stetho:stetho:$version"
  const val okhttp3 = "com.facebook.stetho:stetho-okhttp3:$version"
}

object Sentry {
  private const val version = "2.0.2"

  const val android_gradle_plugin = "io.sentry:sentry-android-gradle-plugin:1.7.31"
  const val android = "io.sentry:sentry-android:$version"
  const val android_core = "io.sentry:sentry-android-core:$version"
  const val android_ndk = "io.sentry:sentry-android-ndk:$version"
  const val core = "io.sentry:sentry-core:$version"
}

object Shimmer {
  const val reycler_view = "com.github.sharish:ShimmerRecyclerView:v1.3"
}

object ThreeTenBp {
  private const val version = "1.4.0"

  const val core = "org.threeten:threetenbp:$version"
  const val no_tz_db = "org.threeten:threetenbp:$version:no-tzdb"
  const val android = "com.jakewharton.threetenabp:threetenabp:1.2.1"
}

object MMPhoneNo {
  private const val version = "1.0.2"

  const val core = "com.aungkyawpaing.mmphonenumber:mmphonenumber:$version"
}

object Wormhole {
  private const val version = "0.3.1"

  const val gradle_plugin = "com.jakewharton.wormhole:wormhole-gradle:$version"

}
