plugins {
  id("com.squareup.sqldelight")
  id("com.android.library")
  kotlin("android")
  kotlin("kapt")
  id("com.squareup.wire")
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
      isMinifyEnabled = true
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

//  sourceSets {
//    getByName("main") {
//      this.java.srcDir("$buildDir/generated/source/wire/")
//    }
//  }

  kotlinOptions {
    jvmTarget = "1.8"
  }
}

sqldelight {
  database("MVoterDb") {
    packageName = "com.popstack.mvoter2015.data.cache"
    dialect = "sqlite:3.24"
  }
}

wire {
  kotlin {
  }
}

dependencies {
  implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
  implementation(project(":data:common"))

  coreLibraryDesugaring("com.android.tools:desugar_jdk_libs:1.0.10")

  implementation(Kotlin.stdblib_jdk)
  implementation(AndroidXCore.core_ktx)

  //Database
  implementation(AndroidXSqlite.sqlite_ktx)
  implementation(SqlDelight.android_driver)

  //Pref
  androidxProtoDataStore()

  //Paging
  implementation(AndroidXPaging.common)

  //Dagger
  daggerJvm()
  daggerAndroid()

  //Testing
  testImplementation(project(":coroutinetestrule"))
  testImplementation("junit:junit:4.13")
  testImplementation(SqlDelight.jvm_driver)
  mockito()
  mockitoAndroid()
  androidXTest()
}

ktlint {
  android.set(true)
}