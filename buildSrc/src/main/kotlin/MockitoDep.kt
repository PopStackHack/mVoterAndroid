import org.gradle.api.artifacts.dsl.DependencyHandler

fun DependencyHandler.mockito() {
  testImplementation(Mockito.core)
  testImplementation(Mockito.inline)
  testImplementation(Mockito.kotlin)
}

fun DependencyHandler.mockitoAndroid() {
  testImplementation(Mockito.android)
  androidTestImplementation(Mockito.core)
  androidTestImplementation(Mockito.inline)
  androidTestImplementation(Mockito.kotlin)
}


object Mockito {
  private const val version = "3.0.0"

  const val core = "org.mockito:mockito-core:$version"
  const val android = "org.mockito:mockito-android:$version"
  const val inline = "org.mockito:mockito-inline:$version"
  const val kotlin = "com.nhaarman.mockitokotlin2:mockito-kotlin:2.2.0"
}