import org.gradle.api.artifacts.dsl.DependencyHandler

fun DependencyHandler.androidXArch() {
  implementation(AndroidXArchLifeCycle.view_model)
  implementation(AndroidXArchLifeCycle.live_data)
  implementation(AndroidXArchLifeCycle.extension)
  implementation(AndroidXArchLifeCycle.lifecycle_java8)
  implementation(AndroidXArchLifeCycle.lifecycle_service)
  testImplementation(AndroidXArchCore.testing)
}

object AndroidXArchLifeCycle {
  private const val version = "2.2.0"

  const val view_model = "androidx.lifecycle:lifecycle-viewmodel-ktx:$version"
  const val saved_state = "androidx.lifecycle:lifecycle-viewmodel-savedstate:$version"
  const val live_data = "androidx.lifecycle:lifecycle-livedata-ktx:$version"
  const val extension = "androidx.lifecycle:lifecycle-extensions:$version"
  const val lifecycle_java8 = "androidx.lifecycle:lifecycle-common-java8:$version"
  const val lifecycle_compiler = "androidx.lifecycle:lifecycle-compiler:$version"
  const val lifecycle_service = "androidx.lifecycle:lifecycle-service:$version"
  const val lifecycle_process = "androidx.lifecycle:lifecycle-process:$version"
  const val lives = "com.snakydesign.livedataextensions:lives:1.2.1"
}

object AndroidXArchCore {
  const val version = "2.1.0"

  const val common = "androidx.arch.core:core-common:$version"
  const val testing = "androidx.arch.core:core-testing:$version"
}