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


object Dagger {
  private const val version = "2.27"

  const val core = "com.google.dagger:dagger:$version"
  const val compiler = "com.google.dagger:dagger-compiler:$version"
  const val android_core = "com.google.dagger:dagger-android:$version"
  const val android_support = "com.google.dagger:dagger-android-support:$version"
  const val android_processor = "com.google.dagger:dagger-android-processor:$version"
}