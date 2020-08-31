// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
  id(KtLint.name) version KtLint.version
}

buildscript {

  repositories {
    maven("https://plugins.gradle.org/m2/")
    google()
    mavenCentral()
    jcenter()
    maven("https://oss.sonatype.org/content/repositories/snapshots/")
  }

  dependencies {
    classpath(CommonLibs.android_gradle_plugin)
    classpath(Kotlin.gradle_plugin)
    classpath(SqlDelight.gradle_plugin)
    classpath(GoogleService.gms_plugin)
    classpath(Firebase.crashlytics_plugin)
  }
}

allprojects {
  repositories {
    google()
    mavenCentral()
    maven(url = "https://dl.bintray.com/vincent-paing/maven")
    maven("https://oss.sonatype.org/content/repositories/snapshots/")
    jcenter()
  }
}

configurations.all {
  resolutionStrategy {
    force(AndroidXRecyclerView.recycler_view)
  }
}

tasks.register("clean", Delete::class) {
  delete(rootProject.buildDir)
}