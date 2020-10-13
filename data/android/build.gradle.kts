plugins {
  id("com.android.library")
  kotlin("android")
  kotlin("kapt")
  id(KtLint.name)
}

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
    }

    getByName("release") {
      isMinifyEnabled = false
      proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
    }
  }

  compileOptions {
    isCoreLibraryDesugaringEnabled = true
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
  }

  kotlinOptions {
    jvmTarget = "1.8"
  }
}

dependencies {
  implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
  implementation(project(":domain"))
  api(project(":data:common"))
  api(project(":data:cache"))

  //Uncomment fakenetwork and comment network for your development environment
  //debugApi(project(":data:fakenetwork"))
  debugApi(project(":data:network"))
  releaseApi(project(":data:network"))

  coreLibraryDesugaring("com.android.tools:desugar_jdk_libs:1.0.10")

  implementation(CommonLibs.timber)
  implementation(AndroidXPaging.common)
  implementation(Kotlin.stdblib_jdk)
  implementation(AndroidXCore.core_ktx)

  implementation(AndroidXDataStore.preferences)

  //Dagger
  daggerJvm()
  daggerAndroid()

  implementation("com.google.android.gms:play-services-base:17.4.0")

  //Testing
  testImplementation(CommonLibs.junit)
  testImplementation("org.robolectric:shadows-playservices:4.3.1")
  testImplementation(project(":coroutinetestrule"))
  mockito()
  mockitoAndroid()
  androidXTest()
}

ktlint {
  android.set(true)
}