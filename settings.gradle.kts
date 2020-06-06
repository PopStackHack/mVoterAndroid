include(":coroutinetestrule")
include(":data:android")
include(":data:network")
include(":data:common")
include(":data:cache")
rootProject.name = "mVoter"
include(":app")
include(":domain")

pluginManagement {
  repositories {
    google()
    mavenCentral()
    jcenter()
    gradlePluginPortal()
  }
}