plugins {
  id("com.android.application")
  kotlin("android")
  kotlin("kapt")
  id("androidx.navigation.safeargs.kotlin")
}


android {
  compileSdkVersion(BuildConfig.targetSdk)
  buildToolsVersion = "29.0.3"

  defaultConfig {
    applicationId = "com.popstack.mvoter2015"
    minSdkVersion(BuildConfig.minSdk)
    targetSdkVersion(BuildConfig.targetSdk)
    versionCode = BuildConfig.versionCode
    versionName = BuildConfig.versionName
    resConfigs("en", "mm")

    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

    buildFeatures {
      viewBinding = true
    }
  }

  buildTypes {
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
  coreLibraryDesugaring("com.android.tools:desugar_jdk_libs:1.0.5")

  implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
  implementation(project(":domain"))
  implementation(project(":data"))

  implementation(Kotlin.stdblib_jdk)
  implementation(KotlinCoroutine.android)

  //AndroidX
  implementation(AndroidXAppCompat.app_compat)
  implementation(AndroidXCore.core_ktx)
  implementation(AndroidXActivity.activity_ktx)
  androidxFragment()
  androidXArch()
  implementation(AndroidXViewPager.view_pager_2)
  implementation(AndroidXViewPager.view_pager)
  implementation(AndroidXRecyclerView.recycler_view)

  //Material
  implementation(Material.material)

  //Constraint Layout
  implementation(AndroidXConstraintLayout.constraint_layout)

  //Dagger
  daggerAndroid()

  //Navigation
  androidxNavigationKtx()

  //ThreeTenBp
  implementation(CommonLibs.timber)

  //Coil
  implementation(Coil.coil)

  //Caigraphy
  implementation(Caligraphy.viewpump)
  implementation(Caligraphy.caligraphy)

  //Test
  testImplementation("junit:junit:4.12")
  mockito()
  mockitoAndroid()
  androidXTest()
  androidXEspresso()
}
