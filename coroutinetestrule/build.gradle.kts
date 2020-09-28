plugins {
    id("java-library")
    id("kotlin")
}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    implementation(project(":domain"))

    implementation(KotlinCoroutine.core)
    api(KotlinCoroutine.test)
    implementation(CommonLibs.junit)
}

