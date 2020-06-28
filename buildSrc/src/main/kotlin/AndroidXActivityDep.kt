import org.gradle.api.artifacts.dsl.DependencyHandler

fun DependencyHandler.androidxActivity() {
  implementation(AndroidXActivity.activity_ktx)
}


object AndroidXActivity {
  private const val version = "1.2.0-alpha05"

  const val activity = "androidx.activity:activity:$version"
  const val activity_ktx = "androidx.activity:activity-ktx:$version"
}
