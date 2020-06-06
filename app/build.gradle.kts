import java.util.Properties

//Load properties
private val properties = Properties()
private val localPropertyFile = project.rootProject.file("local.properties")
properties.load(localPropertyFile.inputStream())

val RELEASE_KEYSTORE_PATH = properties.getProperty("RELEASE_KEYSTORE_PATH")
  .toString()
val RELEASE_KEYSTORE_PASSWORD = properties.getProperty("RELEASE_KEYSTORE_PASSWORD")
  .toString()
val RELEASE_KEY_ALIAS = properties.getProperty("RELEASE_KEY_ALIAS")
  .toString()
val RELEASE_KEY_PASSWORD = properties.getProperty("RELEASE_KEY_PASSWORD")
  .toString()

plugins {
  id("com.android.application")
  kotlin("android")
  kotlin("kapt")
  id(KtLint.name)
}

android {
  compileSdkVersion(BuildConfig.compileSdk)

  defaultConfig {
    applicationId = "com.popstack.mvoter2015"
    minSdkVersion(BuildConfig.minSdk)
    targetSdkVersion(BuildConfig.targetSdk)
    versionCode = BuildConfig.versionCode
    versionName = BuildConfig.versionName
    resConfigs("en", "mm")
    setProperty("archivesBaseName", "mVoter-${BuildConfig.versionName}")

    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

    buildFeatures {
      viewBinding = true
    }
  }

  signingConfigs {
    register("release") {
      storeFile = File(rootDir, RELEASE_KEYSTORE_PATH)
      storePassword = RELEASE_KEYSTORE_PASSWORD
      keyAlias = RELEASE_KEY_ALIAS
      keyPassword = RELEASE_KEY_PASSWORD
    }
  }

  buildTypes {

    getByName("debug") {
      isMinifyEnabled = false
      isDebuggable = true
      versionNameSuffix = "-debug"
      applicationIdSuffix = ".debug"
    }

    getByName("release") {
      isMinifyEnabled = true
      isDebuggable = false
      signingConfig = signingConfigs.getByName("release")
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
  implementation(project(":data:android"))

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

  implementation(Conductor.core)
  implementation(Conductor.viewpager)
  implementation(Conductor.androidx_transition)
  implementation(Conductor.lifecycle)

  //Material
  implementation(Material.material)

  //Constraint Layout
  implementation(AndroidXConstraintLayout.constraint_layout)

  //Dagger
  daggerAndroid()

  //ThreeTenBp
  implementation(CommonLibs.timber)

  //Coil
  implementation(Coil.coil)

  //Caigraphy
  implementation(Caligraphy.viewpump)
  implementation(Caligraphy.caligraphy)

  //Test
  testImplementation("junit:junit:4.12")
  testImplementation(project(":coroutinetestrule"))
  mockito()
  mockitoAndroid()
  androidXTest()
  androidXEspresso()
}

ktlint {
  android.set(true)
}