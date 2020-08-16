import org.gradle.api.artifacts.dsl.DependencyHandler

fun DependencyHandler.androidXTest() {

  testImplementation(AndroidXTest.core_ktx)
  testImplementation(AndroidXTest.runner)
  testImplementation(AndroidXTest.rules)
  testImplementation(AndroidXTest.roboelectric)
  testImplementation(AndroidXTestExt.junit_ktx)
  testImplementation(AndroidXTestExt.truth)

  androidTestImplementation(AndroidXTest.core_ktx)
  androidTestImplementation(AndroidXTest.runner)
  androidTestImplementation(AndroidXTest.rules)
  androidTestImplementation(AndroidXTestExt.junit_ktx)
  androidTestImplementation(AndroidXTestExt.truth)
}


object AndroidXTest {
  private const val version = "1.3.0-alpha05"

  const val core = "androidx.test:core:$version"
  const val core_ktx = "androidx.test:core-ktx:$version"
  const val runner = "androidx.test:runner:$version"
  const val rules = "androidx.test:rules:$version"
  const val roboelectric = "org.robolectric:robolectric:4.4-alpha-5"
}

object AndroidXTestExt {
  private const val version = "1.1.2-alpha05"

  const val junit = "androidx.test.ext:junit:$version"
  const val junit_ktx = "androidx.test.ext:junit-ktx:$version"
  const val truth = "androidx.test.ext:truth:1.3.0-alpha05"
}