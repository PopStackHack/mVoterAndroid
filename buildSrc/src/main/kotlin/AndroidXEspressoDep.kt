import org.gradle.api.artifacts.dsl.DependencyHandler

fun DependencyHandler.androidXEspresso() {
  androidTestImplementation(AndroidXEspresso.core)
  androidTestImplementation(AndroidXEspresso.contrib)
  androidTestImplementation(AndroidXEspresso.intents)
  androidTestImplementation(AndroidXEspresso.idling_resource)
  androidTestImplementation(AndroidXEspresso.idling_concurrent)
}


object AndroidXEspresso {
  private const val version = "3.3.0-alpha05"

  const val core = "androidx.test.espresso:espresso-core:$version"
  const val contrib = "androidx.test.espresso:espresso-contrib:$version"
  const val intents = "androidx.test.espresso:espresso-intents:$version"
  const val idling_resource = "androidx.test.espresso:espresso-idling-resource:$version"
  const val idling_concurrent = "androidx.test.espresso.idling:idling-concurrent:$version"
}