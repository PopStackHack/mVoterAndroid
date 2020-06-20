import org.gradle.api.artifacts.dsl.DependencyHandler


fun DependencyHandler.daggerAndroid() {
  implementation(Dagger.core)
  implementation(Dagger.android_core)
  implementation(Dagger.android_support)
  kapt(Dagger.compiler)
  kapt(Dagger.android_processor)
}


fun DependencyHandler.daggerJvm() {
  implementation(Dagger.core)
  kapt(Dagger.compiler)
}

fun DependencyHandler.daggerHilt() {
  implementation(DaggerHilt.common)
  implementation(DaggerHilt.lifecycle_viewmodel)
  implementation(DaggerHiltAndroid.android)
  kapt(DaggerHilt.compiler)
  kapt(DaggerHiltAndroid.android_compiler)

  androidTestImplementation(DaggerHiltAndroid.android_testing)
//  kaptAndroidTest(DaggerHiltAndroid.android_testing)
}

fun DependencyHandler.daggerAssistedInject() {
  implementation(AssistedInjectDagger.annotations)
  kapt(AssistedInjectDagger.processor)
}

object Dagger {
  private const val version = "2.28"

  const val core = "com.google.dagger:dagger:$version"
  const val compiler = "com.google.dagger:dagger-compiler:$version"
  const val android_core = "com.google.dagger:dagger-android:$version"
  const val android_support = "com.google.dagger:dagger-android-support:$version"
  const val android_processor = "com.google.dagger:dagger-android-processor:$version"
}

object DaggerHilt {
  private const val version = "1.0.0-alpha01"

  const val common = "androidx.hilt:hilt-common:$version"
  const val lifecycle_viewmodel = "androidx.hilt:hilt-lifecycle-viewmodel:$version"
  const val compiler = "androidx.hilt:hilt-compiler:$version"
}

object DaggerHiltAndroid {
  private const val version = "2.28-alpha"

  const val android = "com.google.dagger:hilt-android:$version"
  const val android_testing = "com.google.dagger:hilt-android-testing:$version"
  const val android_compiler = "com.google.dagger:hilt-android-compiler:$version"
  const val gradle_plugin = "com.google.dagger:hilt-android-gradle-plugin:$version"
}

object AssistedInjectDagger {
  private const val version = "0.5.2"

  const val annotations = "com.squareup.inject:assisted-inject-annotations-dagger2:$version"
  const val processor = "com.squareup.inject:assisted-inject-processor-dagger2:$version"
}