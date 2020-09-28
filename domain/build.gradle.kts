plugins {
  id("java-library")
  id("kotlin")
  id(KtLint.name)
}

dependencies {
  implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))

  implementation(Kotlin.stdblib_jdk)
  api(KotlinCoroutine.core)

  implementation(CommonLibs.javaxInject)
  implementation(CommonLibs.mm_ph_number)

  testImplementation(project(":coroutinetestrule"))
  testImplementation(CommonLibs.junit)
  mockito()
}

ktlint {
  android.set(false)
}