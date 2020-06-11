plugins {
  id("com.android.library")
  kotlin("android")
  kotlin("kapt")
  id("dagger.hilt.android.plugin")
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
  implementation(project(":domain"))
  api(project(":data:common"))
  api(project(":data:cache"))
  api(project(":data:network"))

  coreLibraryDesugaring("com.android.tools:desugar_jdk_libs:1.0.5")

  implementation(Kotlin.stdblib_jdk)
  implementation(AndroidXCore.core_ktx)

  //Dagger
  daggerJvm()
  daggerHilt()

  //Testing
  testImplementation("junit:junit:4.12")
  mockito()
  mockitoAndroid()
  androidXTest()
}

ktlint {
  android.set(true)
}