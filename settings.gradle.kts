rootProject.name = "mVoter"
include(":app")
include(":domain")
include(":data")

pluginManagement {
  repositories {
    google()
    mavenCentral()
    jcenter()
    gradlePluginPortal()
  }
}