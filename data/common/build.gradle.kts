plugins {
  id("java-library")
  id("kotlin")
  id("kotlin-kapt")
  id(KtLint.name)
}

dependencies {
  implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
  api(project(":domain"))

  testImplementation(CommonLibs.junit)
  testImplementation(project(":coroutinetestrule"))
  mockito()
  daggerJvm()

  //For paging
  api(AndroidXPaging.common)

  implementation(Kotlin.stdblib_jdk)
  api(KotlinCoroutine.core)

  implementation(CommonLibs.javaxInject)
}

ktlint {
  android.set(false)
}