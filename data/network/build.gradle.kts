import java.util.Properties

plugins {
  id("com.android.library")
  kotlin("android")
  kotlin("kapt")
  id(KtLint.name)
}

private val properties = Properties()
private val localPropertyFile = project.rootProject.file("local.properties")
properties.load(localPropertyFile.inputStream())
val APP_SECRET = properties.getProperty("API_SECRET")
  .toString()

android {
  compileSdkVersion(BuildConfig.compileSdk)

  defaultConfig {
    minSdkVersion(BuildConfig.minSdk)
    targetSdkVersion(BuildConfig.targetSdk)
    versionCode = BuildConfig.versionCode
    versionName = BuildConfig.versionName
    resConfigs("en", "mm")

    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    consumerProguardFiles("consumer-rules.pro")

  }

  buildTypes {
    getByName("debug") {
      isMinifyEnabled = false
      buildConfigField("String", "APP_SECRET", "\"$APP_SECRET\"")
      buildConfigField("String", "BASE_URL", "\"https://mvoter.kwee.online/api/v1/\"")
    }

    getByName("release") {
      isMinifyEnabled = true
      proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
      buildConfigField("String", "APP_SECRET", "\"$APP_SECRET\"")
      buildConfigField("String", "BASE_URL", "\"https://base_url_here\"")
    }
  }

  compileOptions {
    coreLibraryDesugaringEnabled = true
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
  }

  kotlinOptions {
    jvmTarget = "1.8"
  }
}

dependencies {
  implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
  implementation(project(":data:common"))

  coreLibraryDesugaring("com.android.tools:desugar_jdk_libs:1.0.10")

  implementation(Kotlin.stdblib_jdk)
  implementation(AndroidXCore.core_ktx)

  //Pref
  implementation(AndroidXDataStore.preferences)

  //Networking
  implementation(OkHttp.client)
  implementation(OkHttp.logger)
  debugImplementation(Monex.monex)
  releaseImplementation(Monex.no_op)

  implementation(Retrofit.core)
  implementation(Retrofit.moshi_converter)

  moshi()

  //Dagger
  daggerJvm()
  daggerAndroid()

  //Testing
  testImplementation(CommonLibs.junit)
  mockito()
  mockitoAndroid()
  androidXTest()
}

ktlint {
  android.set(true)
}