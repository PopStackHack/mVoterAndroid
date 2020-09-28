import org.gradle.api.artifacts.dsl.DependencyHandler

fun DependencyHandler.androidxProtoDataStore() {
  implementation(AndroidXDataStore.core)
  implementation(Wire.runtime)
}

object AndroidXDataStore {
  private const val version = "1.0.0-alpha01"

  const val core = "androidx.datastore:datastore-core:$version"
  const val preferences = "androidx.datastore:datastore-preferences:$version"
}

object Protobuffer {
  private const val version = "3.13.0"

  const val gradle_plugin = "com.google.protobuf:protobuf-gradle-plugin:0.8.13"

  const val java_lite = "com.google.protobuf:protobuf-javalite:$version"
  const val artifact = "com.google.protobuf:protoc:$version"
}

object Wire {
  private const val version = "3.3.0"

  const val runtime = "com.squareup.wire:wire-runtime:$version"
  const val gradle_plugin = "com.squareup.wire:wire-gradle-plugin:$version"
}