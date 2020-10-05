// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
  id(KtLint.name) version KtLint.version
}

buildscript {

  val kotlin_version by extra("1.4.10")
  repositories {
    maven("https://plugins.gradle.org/m2/")
    google()
    mavenCentral()
    maven("https://jitpack.io")
    jcenter()
    maven("https://oss.sonatype.org/content/repositories/snapshots/")
  }

  dependencies {
    classpath(CommonLibs.android_gradle_plugin)
    classpath(Kotlin.gradle_plugin)
    classpath(SqlDelight.gradle_plugin)
    classpath(Wire.gradle_plugin)
    classpath(GoogleService.gms_plugin)
    classpath(Firebase.crashlytics_plugin)
    "classpath"("org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlin_version")
  }
}

allprojects {
  repositories {
    google()
    mavenCentral()
    maven(url = "https://dl.bintray.com/vincent-paing/maven")
    maven("https://jitpack.io")
    jcenter()
    maven("https://oss.sonatype.org/content/repositories/snapshots/")
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