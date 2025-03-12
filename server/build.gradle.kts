plugins {
    alias(libs.plugins.kotlinJvm)
    alias(libs.plugins.ktor)
    // Serialization
    kotlin("plugin.serialization") version "2.1.10"
    application
}

group = "dev.alexpace.kassist"
version = "1.0.0"
application {
    mainClass.set("dev.alexpace.kassist.ApplicationKt")
    applicationDefaultJvmArgs = listOf("-Dio.ktor.development=${extra["io.ktor.development"] ?: "false"}")
}

dependencies {
    implementation(projects.shared)
    implementation(libs.logback)
    implementation(libs.ktor.server.core)
    implementation(libs.ktor.server.netty)
    testImplementation(libs.ktor.server.tests)
    testImplementation(libs.kotlin.test.junit)

    // Retrofit
    implementation(libs.retrofit)
    implementation(libs.converter.moshi)
    implementation(libs.moshi.kotlin)

    // Serialization
    implementation(libs.kotlinx.serialization.json)
}