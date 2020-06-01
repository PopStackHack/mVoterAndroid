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
  }

  dependencies {
    classpath(CommonLibs.android_gradle_plugin)
    classpath(Kotlin.gradle_plugin)
    classpath(SqlDelight.gradle_plugin)
    classpath(Wormhole.gradle_plugin)
  }
}

allprojects {
  repositories {
    google()
    mavenCentral()
    maven(url = "https://dl.bintray.com/vincent-paing/maven")
    jcenter()
  }
}

tasks.register("clean", Delete::class) {
  delete(rootProject.buildDir)
}