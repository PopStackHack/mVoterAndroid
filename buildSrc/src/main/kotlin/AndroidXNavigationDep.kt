import org.gradle.api.artifacts.dsl.DependencyHandler

fun DependencyHandler.androidxNavigationKtx() {
  implementation(AndroidXNavigation.fragment_ktx)
  implementation(AndroidXNavigation.ui_ktx)
  testImplementation(AndroidXNavigation.testing)
  kapt(AndroidXNavigation.runtime_ktx)
}


object AndroidXNavigation {
  private const val version = "2.3.0-alpha04"

  const val common = "androidx.navigation:navigation-common:$version"
  const val common_ktx = "androidx.navigation:navigation-common-ktx:$version"
  const val fragment = "androidx.navigation:navigation-fragment:$version"
  const val fragment_ktx = "androidx.navigation:navigation-fragment-ktx:$version"
  const val runtime = "androidx.navigation:navigation-runtime:$version"
  const val runtime_ktx = "androidx.navigation:navigation-runtime-ktx:$version"
  const val safe_args_generator = "androidx.navigation:navigation-safe-args-generator:$version"
  const val safe_args_plugin = "androidx.navigation:navigation-safe-args-gradle-plugin:$version"
  const val testing = "androidx.navigation:navigation-testing:$version"
  const val ui = "androidx.navigation:navigation-ui:$version"
  const val ui_ktx = "androidx.navigation:navigation-ui-ktx:$version"
}