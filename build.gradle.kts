// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
  id(KtLint.name) version KtLint.version
}

buildscript {

  val kotlin_version by extra("1.3.72")
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
    classpath(Wormhole.gradle_plugin)
    classpath(DaggerHiltAndroid.gradle_plugin)
    "classpath"("org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlin_version")
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

tasks.register("clean", Delete::class) {
  delete(rootProject.buildDir)
}